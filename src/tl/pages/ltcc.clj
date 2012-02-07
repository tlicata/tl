(ns tl.pages.ltcc
  (:use [hiccup.page-helpers :only [link-to]])
  (:require [com.reasonr.scriptjure :as script]
            [clj-redis.client :as redis]
            [ring.util.codec :as codec]))

(def db-url (System/getenv "REDISTOGO_URL"))
(def db (redis/init (when db-url {:url db-url})))

(defn add [key val]
  (redis/set db key val))
(defn keys []
  (redis/keys db))
(defn retrieve [key]
  (redis/get db key))

(defn row-by-key [key]
  (let [safe (codec/url-encode key)
        value (codec/url-encode (retrieve key))]
    [:tr [:td safe] [:td value]]))

(defn ltcc []
  (let [keys (keys)
        rows (map row-by-key keys)]
    {:title ["Ltcc"]
     :body [[:div (vec (concat [:table] rows))]]
     :js ["/js/bin/all.js"]}))
