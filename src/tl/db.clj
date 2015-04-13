(ns tl.db
  (:require [taoensso.carmine :as car :refer (wcar)]))

(def server-conn (if-let [redis-cloud (System/getenv "REDISCLOUD_URL")]
                   {:pool {} :spec {:uri redis-cloud}}
                   {:pool {} :spec {:host "localhost" :port 6379}}))

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

(wcar* (car/ping)
       (car/set "foo" "bar")
       (car/get "foo"))
