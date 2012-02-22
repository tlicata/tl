(ns tl.pages.ltcc
  (:use [hiccup.core :only [escape-html]]
        [hiccup.page-helpers :only [link-to]]
        [noir.core :only [defpage]]
        [ring.util.response :only [redirect]]
        [tl.pages.global :only [pagify]])
  (:require [com.reasonr.scriptjure :as script]
            [clj-redis.client :as redis]
            [hiccup.form-helpers :as form]))

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

(defn get-form-for-add []
  (form/form-to [:post "/admin/ltcc/"]
                (form/text-field :foo)
                (form/text-field :bar)
                (form/submit-button "submit")))
(defn get-form-for-delete [key]
  (form/form-to [:delete "/admin/ltcc/"]
                (form/hidden-field :foo key)
                (form/submit-button "delete")))

(defn get-row-by-key [key]
  (let [safe (escape-html key)
        value (escape-html (retrieve key))
        delete (get-form-for-delete key)]
    [:tr [:td safe] [:td value] [:td delete]]))

(defpage "/ltcc/" []
  (let [keys (get-all-keys)
        rows (map get-row-by-key keys)]
    (pagify
     {:title ["Ltcc"]
      :body [[:div (get-form-for-add)]
             [:div#ltcc (vec (concat [:table] rows))]]
      :js ["/js/bin/all.js"]})))

(defpage [:post "/admin/ltcc/"] {:keys [foo bar]}
  (do
    (add foo bar)
    (redirect "/ltcc/")))

(defpage [:delete "/admin/ltcc/"] {:keys [foo]}
  (do
    (delete [foo])
    (redirect "/ltcc/")))
