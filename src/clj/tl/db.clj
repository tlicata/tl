(ns tl.db
  (:require [clojure.data.json :as json]
            [clojure.string :as string]
            [taoensso.carmine :as car :refer (wcar)]))

(def server-conn (if-let [redis-cloud (System/getenv "REDISCLOUD_URL")]
                   {:pool {} :spec {:uri redis-cloud}}
                   {:pool {} :spec {:host "localhost" :port 6379}}))

(defmacro safely [form] `(try ~form (catch Exception _# nil)))
(defmacro wcar* [& body] `(safely (car/wcar server-conn ~@body)))
(defmacro wcarv* [& body] `(safely (car/wcar server-conn :as-pipeline ~@body)))

(defn get-today []
  (let [sdf (java.text.SimpleDateFormat. "yyyyMMdd")
        today (.getTime (. java.util.Calendar getInstance))]
    (.format sdf today)))


;; youtube history

(def youtube-key "youtube:")

(defn youtube-played [video-id]
  (let [key (str youtube-key video-id)
        today (get-today)]
    (wcar* (car/hincrby key "count" 1)
           (car/hsetnx key "first-seen" today)
           (car/hset key "last-seen" today))))
(defn youtube-get-keys [keys]
  (let [videos (wcarv* (mapv car/hgetall keys))]
    (map (partial apply hash-map)
         (map (fn [video key]
                (conj video "video-id" (string/replace key youtube-key "")))
              videos keys))))
(defn youtube-get-all []
  (let [keys (wcar* (car/keys (str youtube-key "*")))]
    (youtube-get-keys keys)))

(defn youtube-update-title [ids-and-titles]
  (wcar* (mapv (fn [[id title]]
                 (car/hset (str youtube-key id) "title" title))
               ids-and-titles)))
(defn youtube-update-id [old new]
  (wcar* (car/rename (str youtube-key old) (str youtube-key new))))

;; playlists

(def playlist-key "playlist:")

(defn youtube-list-get [name]
  (when-let [data (wcar* (car/get (str playlist-key name)))]
    (json/read-str data)))
(defn youtube-list-show [name]
  (let [ids (youtube-list-get name)
        keys (map (partial str youtube-key) ids)]
    (youtube-get-keys keys)))
(defn youtube-list-set [name videos]
  (wcar* (car/set (str playlist-key name) (json/write-str videos))))
(defn youtube-list-add [name video]
  (youtube-list-set name (conj (youtube-list-get name) video)))
(defn youtube-list-remove
  ([name video]
   (youtube-list-remove name video 1))
  ([name video count]
   (youtube-list-remove name video count []))
  ([name video count replace]
   (let [parts (split-with (partial not= video) (youtube-list-get name))
         rest (second parts)
         swap (if (= replace :swap) (reverse (take count rest)) replace)]
     (youtube-list-set name (concat (first parts) swap (drop count rest))))))
(defn youtube-list-demote [name video]
  (youtube-list-remove name video 2 :swap))
