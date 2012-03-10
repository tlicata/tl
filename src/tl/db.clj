(ns tl.db
  (:require [clj-redis.client :as redis]
            [noir.util.crypt :as crypt]))

(def db-url (System/getenv "REDISTOGO_URL"))
(def db (redis/init (when db-url {:url db-url})))

(defn user-exists? [username]
  (redis/sismember db "users" username))

(defn add-user [username pass]
  (if (user-exists? username)
    (throw (Exception. "User already exists"))
    (do
      ;; tranaction?
      (redis/sadd db "users" username)
      (redis/set db (str username ":hash") (crypt/encrypt pass)))))
(defn delete-user [username]
  (try
    (do
      (redis/srem db "users" username)
      (redis/del db [(str username ":hash")]))
    (catch Exception e nil)))
(defn get-stored-pass [username]
  (try (redis/get db (str username ":hash"))
       (catch Exception e "unreachable")))
(defn get-all-users []
  (try (redis/smembers db "users")
       (catch Exception e ["database"])))

