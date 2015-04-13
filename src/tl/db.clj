(ns tl.db
  (:require [taoensso.carmine :as car :refer (wcar)]))

(def server-conn (if-let [redis-cloud (System/getenv "REDISCLOUD_URL")]
                   {:pool {} :spec {:uri redis-cloud}}
                   {:pool {} :spec {:host "localhost" :port 6379}}))

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

(defn get-today []
  (let [sdf (java.text.SimpleDateFormat. "yyyyMMdd")
        today (.getTime (. java.util.Calendar getInstance))]
    (.format sdf today)))

(defn youtube-played [video-id]
  (let [key (str "youtube:" video-id)
        today (get-today)]
    (wcar* (car/hincrby key "count" 1)
           (car/hsetnx key "first-seen" today)
           (car/hset key "last-seen" today))))
