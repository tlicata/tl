(ns com.timothylicata.pages
	(:use compojure.html))

(defn print-home-page
  []
  (html
    [:html
     [:head
      [:title "Tim's Online World"]
      (include-css "/css/main.css")]
;;    (include-js "/js/combined.js")]
     [:body
      [:div#page
       [:div#header [:h1 (link-to "http://www.timothylicata.com" "Tim's Online World")]]
       [:div#nav
        [:ul
         [:li (link-to "about.html" "About")]
         [:li (link-to "contact.html" "Contact")]
         [:li (link-to "faq.html" "FAQ")]
         [:li (link-to "work.html" "Work")]]]
       [:div#body
        [:p "Index prease"]]
       [:div#footer
        [:p "Powered by "
         (link-to "http://code.google.com/appengine/" "Google App Engine") ", "
         (link-to "http://clojure.org" "Clojure") " and  "
         (link-to "http://github.com/weavejester/compojure" "Compojure")]]]]]))
