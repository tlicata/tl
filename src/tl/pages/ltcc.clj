(ns tl.pages.ltcc
  (:use [hiccup.core :only [escape-html]]
        [hiccup.page-helpers :only [link-to]]
        [noir.core :only [defpage]]
        [ring.util.response :only [redirect]]
        [tl.pages.global :only [pagify]])
  (:require [com.reasonr.scriptjure :as script]
            [hiccup.form-helpers :as form]
            [tl.user :as user]))

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
        value (escape-html (user/stored-pass key))
        delete (get-form-for-delete key)]
    [:tr [:td safe] [:td value] [:td delete]]))

(defpage "/ltcc/" []
  (let [keys (user/all)
        rows (map get-row-by-key keys)]
    (pagify
     {:title ["Ltcc"]
      :body [[:div (get-form-for-add)]
             [:div#ltcc (vec (concat [:table] rows))]]
      :js ["/js/bin/all.min.js"]})))

(defpage [:post "/admin/ltcc/"] {:keys [foo bar]}
  (do
    (user/add foo bar)
    (redirect "/ltcc/")))

(defpage [:delete "/admin/ltcc/"] {:keys [foo]}
  (do
    (user/delete foo)
    (redirect "/ltcc/")))
