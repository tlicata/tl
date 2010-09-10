(ns tl.pages.global
  (:use [hiccup.core :only [html]]
	[hiccup.page-helpers :only [include-css include-js link-to]]))

(def ajax-api "http://www.google.com/jsapi?key=ABQIAAAAleydYZEjUE7f9RhdHI8NtBS6-BlYLfNinRJgsDPbRk1Y0-Rs-hR5dAJbQkD-YuuQBRVFZP5E1evO4Q")
(def jquery "http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js")
(def grid "http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css")

(defn css
  []
  (let [global ["/css/main.css" grid]]
    (apply include-css global)))

(defn js
  [js-list]
  (let [global-js [ajax-api jquery]]
	(apply include-js (concat global-js js-list))))

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

(defn header []
  [:div#hd
   (link-to "/googlemaps.html" "Google Maps")
   (link-to "/polymaps.html" "Polymaps")])
(defn footer []
  [:div#ft
   [:ul
	[:li (link-to "http://clojure.org" "Clojure")]
	[:li (link-to "http://code.google.com/appengine/" "Google App Engine")]
	[:li (link-to "http://www.crockford.com/javascript/" "Javascript")]]])

(defn page
  [request info]
  (html
    [:html
     [:head
	  [:title (str "Tim's Online World - " (:title info))]
	  (css)
	  (js (:js info))]
     [:body
      [:div#doc4
	   (header)
	   (apply conj [:div] (:blurbs info))
	   (footer)]]]))
