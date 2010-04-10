(ns tl.appengine.datastore
  (:import (com.google.appengine.api.datastore DatastoreConfig DatastoreServiceFactory Entity Key Query KeyFactory))
  (:refer-clojure :exclude [get]))

(defn create-key
  "Creates a new Key from the given kind and id. If a parent key is given
the new key will be a child of the parent key."
  ([kind id]
     (create-key nil kind id))
  ([#^Key parent kind id]
     (KeyFactory/createKey parent kind (if (integer? id) (Long/valueOf (str id)) (str id)))))

(defn datastore
  "Creates a DatastoreService using the default or the provided
configuration."
  ([] (datastore DatastoreConfig/DEFAULT))
  ([configuration] (DatastoreServiceFactory/getDatastoreService configuration)))

(defn key->string
  "Converts the given Key into a websafe string."
  [key] (KeyFactory/keyToString key))

(defn string->key
  "Converts a String-representation of a Key into the Key instance it represents."
  [string] (KeyFactory/stringToKey string))

(defn entity->map
  "Converts an instance of com.google.appengine.api.datastore.Entity
  to a PersistentHashMap with properties stored under keyword keys,
  plus the entity's kind stored under :kind and key stored under :key."
  [#^Entity entity]
  (reduce #(assoc %1 (keyword (key %2)) (val %2))
    {:kind (.getKind entity) :key (.getKey entity)}
    (.entrySet (.getProperties entity))))

(declare properties)

(defn map->entity
  "Converts a PersistentHashMap or struct into a Entity instance. The
map must have the key or kind of the entity stored under the :key or
a :kind keywords."
  [map]
  (reduce #(do (.setProperty %1 (name (first %2)) (second %2)) %1)
          (Entity. (or (:key map) (:kind map)))
          (properties map)))

(defmulti properties
  "Returns the properties of the given record as a map."
  class)

(defmethod properties Entity [#^Entity entity]
  (properties (entity->map entity)))

(defmethod properties :default [map]
  (dissoc (merge {} map) :key :kind))

(defmulti get
  "Retrieves a PersistentHashMap of an Entity in the datastore. The
  entity can be retrieved by an instance of Key, Entity or a
  PersistentHashMap with a :key keyword."
  class)

(defmethod get Entity [#^Entity entity]
  (get (.getKey entity)))

(defmethod get Key [key]
  (entity->map (.get (datastore) key)))

(defmethod get :default [map]
  (if-let [key (:key map)]
    (entity->map (.get (datastore) key))))

(defmulti put
  "Puts the given record into the datastore and returns a
  PersistentHashMap of the record. The record must be an instance of
  Entity or PersistentHashMap."
  class)

(defmethod put Entity [#^Entity entity]
  (let [key (.put (datastore) entity)]
    (assoc (entity->map entity) :key key)))

(defmethod put :default [map]
  (put (map->entity map)))

(defn find-all
  "Executes the given com.google.appengine.api.datastore.Query
  and returns the results as a lazy sequence of items converted with entity->map."
  [#^Query query]
  (let [data-service (datastore)
        results (.asIterable (.prepare data-service query))]
    (map entity->map results)))

;; (defn create
;;   "Takes a map of keyword-value pairs or struct and puts a new Entity in the Datastore.
;;   The map or struct must include a :kind String.
;;   Returns the saved Entity converted with entity->map (which will include the assigned :key)."
;;   ([item] (create item nil))
;;   ([item #^Key parent-key]
;;     (let [kind (item :kind)
;;           properties (dissoc (merge {} item) :kind) ; converts struct to map
;;           entity (if parent-key (Entity. kind parent-key) (Entity. kind))]
;;       (doseq [[prop-name value] properties]
;;         (.setProperty entity (name prop-name) value))
;;       (let [key (.put (datastore) entity)]
;;         (assoc (entity->map entity) :key key)))))

;; (defn create
;;   "Takes a map of keyword-value pairs or struct and puts a new Entity in the Datastore.
;;   The map or struct must include a :kind String.
;;   Returns the saved Entity converted with entity->map (which will include the assigned :key)."
;;   ([item] (create item nil))
;;   ([item #^Key parent-key]
;;      (put (assoc item :key (if parent-key (Entity. (:kind item)
;;   parent-key) (Entity. (:kind item)))))))

(defn create [record]
  (put record))

(defmulti update
  "Updates the record with the given properites. The record must be an
  instance of Entity, Key or PersistentHashMap."
  (fn [record attributes] (class record)))

(defmethod update Entity [#^Entity entity attributes]
  (doseq [[attribute value] attributes]
    (.setProperty entity (name attribute) value))
  (put entity))

(defmethod update Key [#^Key key attributes]
  (update (get key) attributes))

(defmethod update :default [map attributes]
  (update (map->entity map) attributes))

(defn delete
  "Deletes the identified entities."
  [& #^Key keys]
  (.delete (datastore) keys))

