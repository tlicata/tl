(ns tl.db
  (:require [clj-redis.client :as redis]))

(def db-url (System/getenv "REDISTOGO_URL"))
(def db (redis/init (when db-url {:url db-url})))

(defn add [key val]
  (try (redis/set db key val)
       (catch Exception e nil)))
(defn delete [key]
  (try (redis/del db key)
       (catch Exception e nil)))
(defn retrieve [key]
  (try (redis/get db key)
       (catch Exception e "unreachable")))
(defn get-all-keys []
  (try (redis/keys db)
       (catch Exception e ["database"])))

