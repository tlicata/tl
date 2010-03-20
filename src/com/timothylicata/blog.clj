(ns com.timothylicata.blog
  (:import (com.google.appengine.api.datastore Query))
  (:require [appengine.datastore :as ds])
  (:use clojure.contrib.str-utils)
  (:use compojure.html compojure.http.helpers compojure.http.session)
  (:use com.timothylicata.util))

(defn make-tag [tag]
  ;save tag in db?
  tag)

(defn make-category [category]
  ;save category in db?
  category)

(defn- validate* [arg]
  `(when (empty? ~arg)
     (die "You left '" (str '~arg) "' blank.  Try again.")))

(defmacro validate [& args]
  `(do ~@(map validate* args)))

(defn- partial-post-from-params
  [title category tags markdown]
  (validate title category tags markdown)
  {:kind "article"
   :date (java.util.Date.)
   :title title
   :markdown markdown
   :category category
   :tags tags})

(defn- post-from-params [id & args]
  (validate id)
  (merge {:id id} (apply partial-post-from-params args)))

(defn do-add-post [& args]
  nil)
  ;(add-post (apply partial-post-from-params args))
  ;(redirect-to "/admin.html"))

(defn edit-post [post]
  (ds/update {:id post} post))

(defn do-edit-post [& args]
  (edit-post (apply post-from-params args))
  (redirect-to "/admin.html"))

(defn find-all []
  (ds/find-all (doto (Query. "article") (.addSort "date"))))

(defn posts []
  (find-all))
