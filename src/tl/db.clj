(ns tl.db
  (:require [clojure.string :as string]
            [taoensso.carmine :as car :refer (wcar)]))

(def server-conn (if-let [redis-cloud (System/getenv "REDISCLOUD_URL")]
                   {:pool {} :spec {:uri redis-cloud}}
                   {:pool {} :spec {:host "localhost" :port 6379}}))

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

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

(defn youtube-get-all []
  (let [keys (wcar* (car/keys (str youtube-key "*")))
        videos (wcar* (mapv car/hgetall keys))]
    (map (partial apply hash-map)
         (map (fn [video key]
                (conj video "video-id" (string/replace key youtube-key "")))
              videos keys))))

(defn youtube-update-title [ids-and-titles]
  (wcar* (mapv (fn [[id title]]
                 (car/hset (str youtube-key id) "title" title))
               ids-and-titles)))

;; playlists

(defn youtube-list-add [name video])
(defn youtube-list-create [name])
(defn youtube-list-get [name]
  [{"video-id" "some_id"  "title" "awesome pants"}
   {"video-id" "other_id" "title" "awesome sauce"}])
