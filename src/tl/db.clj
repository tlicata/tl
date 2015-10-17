(ns tl.db
  (:require [clojure.string :as string]
            [taoensso.carmine :as car :refer (wcar)]))

(def server-conn (if-let [redis-cloud (System/getenv "REDISCLOUD_URL")]
                   {:pool {} :spec {:uri redis-cloud}}
                   {:pool {} :spec {:host "localhost" :port 6379}}))

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))
(defmacro wcarv* [& body] `(car/wcar server-conn :as-pipeline ~@body))

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

;; playlists

(def playlist-key "playlist:")

(defn youtube-list-add [name video]
  (wcar* (car/lpush (str playlist-key name) video)))
(defn youtube-list-remove [name video]
  (wcar* (car/lrem (str playlist-key name) 0 video)))
(defn youtube-list-get [name]
  (let [ids (wcar* (car/lrange (str playlist-key name) 0 -1))
        keys (map (partial str youtube-key) ids)]
    (youtube-get-keys keys)))
