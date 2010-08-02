(ns tl.pages.global
  (:use [hiccup.core :only [html]]
	[hiccup.page-helpers :only [include-css link-to]]))

(defn css
  []
  (let [global-styles (list
    "http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css"
    "css/main.css")]
    (apply include-css global-styles)))

(defn nav-links []
  [:ul
   [:li (link-to "/programming.html" "Programmer")]
   [:li (link-to "/golf.html" "Golfer")]
   [:li (link-to "/youtubes.html" "Rock 'N' Roll Enthusiast")]])

(defn admin-links [request]
  ;(let [user-info (:appengine/user-info request)
  ;      user-service (:user-service user-info)
  ;      admin (and (:user user-info) (. user-service isUserAdmin))]
  (let [admin true]
    (if admin
      [:ul.admin
       [:li (link-to "/admin/" "Admin")]])))
;       [:li (link-to (. user-service createLogoutURL "/") "Log Out")]]
;      [:ul.admin
;       [:li (link-to (. user-service createLoginURL "/") "Log In")]])))

(defn blurb [content & more]
  (apply conj [:div.blurb] content more))

(defn header
  [request]
  [:div#hd
   [:h1 (link-to "/" "Timothy Licata")]
   [:div#nav (nav-links) (admin-links request)]])

(defn sidebar [request]
  [:div#sidebar
   [:div.blurb
    [:h1 "A few of my favorite things."]
    [:ul
     [:li "Paul Graham link or two"]
     [:li "JavaScript link or two"]
     [:li "Clojure link or two"]
     [:li "HotPads.com"]
     [:li "TheSixtyOne.com"]]]])

(defn footer [request]
  [:div#ft
   [:p "Powered by "
    (link-to "http://code.google.com/appengine/" "Google App Engine") ", "
    (link-to "http://clojure.org" "Clojure") " and  "
    (link-to "http://github.com/weavejester/compojure" "Compojure")]])

(defn page
  [request info]
  (html
    [:html
     [:head [:title (:title info)] (css)]
     [:body
      [:div#doc2.yui-t5
       (header request)
       [:div#bd
        [:div#yui-main [:div.yui-b (:body info)]]
        [:div.yui-b (sidebar request)]]
       (footer request)]]]))
