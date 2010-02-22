(ns com.timothylicata.pages
	(:use compojure.html))

(defn header []
  [:div#header [:h1 (link-to "/" "Tim's Online World")]])

(defn nav []
  [:div#nav
   [:ul
    [:li (link-to "about.html" "About")]
    [:li (link-to "contact.html" "Contact")]
    [:li (link-to "work.html" "Work")]]])

(defn footer []
  [:div#footer
   [:p "Powered by "
    (link-to "http://code.google.com/appengine/" "Google App Engine") ", "
    (link-to "http://clojure.org" "Clojure") " and  "
    (link-to "http://github.com/weavejester/compojure" "Compojure")]])

(defn home-page
  []
  (html
    [:html
     [:head
      [:title "Tim's Online World"]
      (include-css "/css/main.css")]
     [:body
      (header)
      (nav)
      [:div#body [:p "Index prease"]]
      (footer)]]))

(defn about
  []
  (html
    [:html
     [:head
      [:title "About Tim"]
      (include-css "/css/main.css")]
     [:body
      (header)
      (nav)
      [:div#body
       [:h3 "Manifesto"]
       [:p "Manifestos are cool.  Everyone knows that.  According to "
        (link-to "http://en.wikipedia.org/wiki/Manifesto" "Wikipedia") ","]
       [:blockquote "A manifesto is a public declaration of principles and intentions, often political in nature. However, manifestos relating to religious belief are generally referred to as a creed. Manifestos may also be life stance-related."]
       [:p "I think I'm after the 'Life Stance' part.  I don't have a concrete one yet but I like the idea so I'm hoping having this here will force me to write one."]
       [:h3 "FAQ"]
       [:div.question
        [:p "Do you want to make more money?"]]
       [:div.answer
        [:p "Sure, we all do."]]]
      (footer)]]))
