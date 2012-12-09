(ns tl.pages.home
  (:use [clojure.data.json :only [read-json]]
        [clojure.string :only [join]]
        [clojure.pprint :only [pprint]]
        [noir.core :only [defpage]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [include-js]]
        [tl.pages.global :only [pagify]])
  (:require [hiccup.form :as form]
            [noir.response :as resp]
            [tl.user :as user]))

(defpage "/" []
  (pagify {:title ["Home"]
           :body [[:div#particles]]
           :js #{"/js/lib/three.js" "/js/particles.js"}}))

(defpage "/admin/" request
  (pagify {:title ["Admin"]
           :body [[:div [:p "Admin"]]
                  [:div (link-to "/logout/" "log out")]]}))

(defn login-form []
  (form/form-to [:post "/login/"]
                (form/text-field :username)
                (form/password-field :password)
                (form/submit-button "login")))

(defpage "/login/" []
  (if (user/admin?)
    (resp/redirect "/admin/")
    (pagify {:title ["Login"]
             :body [(login-form)]})))

(defpage [:post "/login/"] {:as user}
  (if (user/login! user)
    (resp/redirect "/admin/")
    (pagify {:title ["Login"]
             :body [[:p "invalid login attempt"]
                    (login-form)]})))

(defpage "/logout/" []
  (do
    (user/logout!)
    (resp/redirect "/login/")))

(defpage "/cljs/" []
  (pagify {:title ["Cljs Trial"]
           :body [[:div
                   [:input#lname {:type "text" :placeholder "place holder"}]
                   [:input#search-btn {:type "button" :value "Get Stats!"}]]
                  [:script {:type "text/javascript" :src "/js/bin/all.js"}]]}))
