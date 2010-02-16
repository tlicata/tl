(ns com.timothylicata.servlet
	(:gen-class :extends javax.servlet.http.HttpServlet)
	(:use compojure.http compojure.html)
  (:import (com.google.appengine.api.users UserServiceFactory)))

(defn print-home-page
  []
  (html
    [:html
     [:head
      [:title "Tim's Online World"]]
;;    (include-css "/css/main.css")]
;;    (include-js "/js/combined.js")]
     [:body
      [:div#page
       [:div#header [:h1 (link-to "http://www.timothylicata.com" "Tim's Online World")]]
       [:div#nav
        [:ul
         [:li (link-to "about.html" "About")]]]
       [:div#body
        [:p "Index prease"]]
       [:div#footer
        [:p "Powered by "
         (link-to "http://code.google.com/appengine/" "Google App Engine") ", "
         (link-to "http://clojure.org" "Clojure") " and  "
         (link-to "http://github.com/weavejester/compojure" "Compojure")]]]]]))

(defroutes tl
  (GET "/"
    (print-home-page))
  (GET "/*"
    (or (serve-file (:* params)) :next))
  (ANY "*"
    (page-not-found)))

(defservice tl)
