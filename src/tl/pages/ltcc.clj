(ns tl.pages.ltcc
  (:use [hiccup.core :only [escape-html]]
        [hiccup.page-helpers :only [link-to]]
        [ring.util.response :only [redirect]])
  (:require [com.reasonr.scriptjure :as script]
            [clj-redis.client :as redis]
            [hiccup.form-helpers :as form]))

(def db-url (System/getenv "REDISTOGO_URL"))
(def db (redis/init (when db-url {:url db-url})))

(defn add [key val]
  (redis/set db key val))
(defn delete [key]
  (redis/del db key))
(defn keys []
  (redis/keys db))
(defn retrieve [key]
  (redis/get db key))

(defn get-form-for-add []
  (form/form-to [:post ""]
                (form/text-field :foo)
                (form/text-field :bar)
                (form/submit-button "submit")))
(defn get-form-for-delete [key]
  (form/form-to [:delete ""]
                (form/hidden-field :foo key)
                (form/submit-button "delete")))

(defn get-row-by-key [key]
  (let [safe (escape-html key)
        value (escape-html (retrieve key))
        delete (get-form-for-delete key)]
    [:tr [:td safe] [:td value] [:td delete]]))

(defn ltcc []
  (let [keys (keys)
        rows (map get-row-by-key keys)]
    {:title ["Ltcc"]
     :body [[:div (get-form-for-add)]
            [:div (vec (concat [:table] rows))]]
     :js ["/js/bin/all.js"]}))

(defn ltcc-add [foo bar]
  (do
    (add foo bar)
    (redirect "/ltcc/")))

(defn ltcc-remove [foo]
  (do
    (delete [foo])
    (redirect "/ltcc/")))
