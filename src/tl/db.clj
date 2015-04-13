(ns tl.db
  (:require [taoensso.carmine :as car :refer (wcar)]))

(def server-conn (if-let [redis-cloud (System/getenv "REDISCLOUD_URL")]
                   {:pool {} :spec {:uri redis-cloud}}
                   {:pool {} :spec {:host "localhost" :port 6379}}))

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

(defn youtube-played [video-id]
  (let [key (str "youtube:" video-id)]
    (if (= 1 (wcar* (car/exists key)))
      (wcar* (car/hincrby key "count" 1))
      (wcar* (car/hmset key "count" 1)))))
