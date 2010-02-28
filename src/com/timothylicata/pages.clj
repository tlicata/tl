(ns com.timothylicata.pages
	(:use compojure.html))

(defn css
  []
  (let [global-styles (list "/css/main.css" "http://yui.yahooapis.com/2.8.0r4/build/fonts/fonts-min.css")]
    (apply include-css global-styles)))

(defn header
  [request]
  [:div#header [:h1 (link-to "/" "Tim's Online World")]])

(defn nav
  [request]
  (let [user-info (:appengine/user-info request)
        user-service (:user-service user-info)
        admin (and
                (:user user-info)
                (. user-service isUserAdmin))]
    [:div#nav
     (if admin
       [:ul
        [:li (link-to "about.html" "About")]
        [:li (link-to "contact.html" "Contact")]
        [:li (link-to "work.html" "Work")]
        [:li (link-to (. user-service createLogoutURL (:uri request)) "Log Out")]]
       [:ul
        [:li (link-to "contact.html" "Contact")]
        [:li (link-to (. user-service createLoginURL (:uri request)) "Log In")]])]))

(defn footer [request]
  [:div#footer
   [:p "Powered by "
    (link-to "http://code.google.com/appengine/" "Google App Engine") ", "
    (link-to "http://clojure.org" "Clojure") " and  "
    (link-to "http://github.com/weavejester/compojure" "Compojure")]])

(defn home-page
  [request]
  (html
    [:html
     [:head [:title "Tim's Online World"] (css)]
     [:body
      (header request)
      (nav request)
      [:div#body
       [:div.blurb
        [:ul
         [:li "server-port: " (:server-port request)]
         [:li "server-name: " (:server-name request)]
         [:li "remote-addr: " (:remote-addr request)]
         [:li "uri: " (:uri request)]
         [:li "query-string: " (:query-string request)]
         [:li "scheme: " (:scheme request)]
         [:li "request-method: " (:request-method request)]
         [:li "headers: " (:headers request)]
         [:li "content-type: " (:content-type request)]
         [:li "character-encoding: " (:character-encoding request)]
         [:li "body: " (:body request)]
         [:li "appengine/user-info: " (:appengine/user-info request)]]]]
      (footer request)]]))

(defn about
  [request]
  (html
    [:html
     [:head [:title "About Tim"] (css)]
     [:body
      (header request)
      (nav request)
      [:div#body
       [:div.blurb
        [:h3 "Manifesto"]
        [:p "Manifestos are cool.  Everyone knows that.  According to "
         (link-to "http://en.wikipedia.org/wiki/Manifesto" "Wikipedia") ","]
        [:blockquote "A manifesto is a public declaration of principles and intentions, often political in nature. However, manifestos relating to religious belief are generally referred to as a creed. Manifestos may also be life stance-related."]
        [:p "I think I'm after the "
         (link-to "http://en.wikipedia.org/wiki/Life_stance" "Life Stance")
         " part.  Let's take a look at some of the notable manifestos listed."]
        [:ul
         [:li (link-to "http://en.wikipedia.org/wiki/United_States_Declaration_of_Independence" "United States Declaration of Independence")]
         [:li (link-to "http://en.wikipedia.org/wiki/The_Communist_Manifesto" "The Communist Manifesto")]
         [:li (link-to "http://en.wikisource.org/wiki/Industrial_Society_and_Its_Future" "Industrial Society and Its Future")]
         [:li (link-to "http://en.wikipedia.org/wiki/The_Cathedral_and_the_Bazaar" "The Cathedral and the Bazaar")]
         [:li (link-to "http://www.phrack.org/issues.html?issue=7&id=3&mode=txt" "The Hacker's Manifesto")]
         [:li (link-to "http://en.wikipedia.org/wiki/Agile_Manifesto" "Agile Manifesto (?)")]]
        [:p "Most of these seem to be extreme in both their viewpoints and their word counts.  That's not really my style.  I believe it was Ferris Bueller who said:"]
         [:blockquote "Isms in my opinion are not good. A person should not believe in an ism, he should believe in himself. I quote John Lennon:"
          [:blockquote "I don't believe in Beatles, I just believe in me."] "Good point there.  After all, he was the Walrus."]
        [:p "So that's me.  I don't believe in an Isms, I just believe in myself and, of course, my 'Life Stance'.  That I haven't written yet."]]
       [:div.blurb
        [:h3 "FAQ"]
        [:div.question
         [:p "Do you want to make more money?"]]
        [:div.answer
         [:p "Sure, we all do."]]]]
      (footer request)]]))

(defn contact
  [request]
  (html
    [:html
     [:head [:title "Contact Tim"] (css)]
     [:body
      (header request)
      (nav request)
      [:div
       [:div.blurb
        [:h3 "Email"]
        [:p "The best way to get ahold of me is through email.  tim [at] [this domain]."]]]
      (footer request)]]))

(defn work
  [request]
  (html
    [:html
     [:head [:title "Tim's Work"] (css)]
     [:body
      (header request)
      (nav request)
      [:div
       [:div.blurb
        [:h3 "HotPads.com"]
        [:p "My first job"]]
       [:div.blurb
        [:h3 "Synacor, Inc."]
        [:p "My second job"]]
       [:div.blurb
        [:h3 "This Site"]
        [:p "One of the reasons I built this site was to give myself a programming playground.  This site is hosted on Google App Engine.  It is written in Clojure, which is a really cool functional language that runs on the JVM.  Luckily, someone wrote a very nice web application library for Clojure called Compojure.  On the client side I plan on refining my HTML/CSS/JS.  My JavaScript library of choice is JQuery.  And sometimes I add a Flash or Flex app."]]
       [:div.blurb
        [:h3 "Resume"]]]
      (footer request)]]))
