(ns com.timothylicata.pages
	(:use compojure.html))

(defn css
  []
  (let [global-styles (list
    "http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css"
    "/css/main.css")]
    (apply include-css global-styles)))

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
        [:li (link-to (. user-service createLogoutURL "/") "Log Out")]]
       [:ul
        [:li (link-to "contact.html" "Contact")]
        [:li (link-to (. user-service createLoginURL (:uri request)) "Log In")]])]))

(defn header
  [request]
  [:div#hd
   [:h1 (link-to "/" "Tim's Online World")]
   (nav request)])

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

(defn home-page
  [request]
  (page request
    {:title "Tim's Online World"
     :body
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
       [:li "appengine/user-info: " (:appengine/user-info request)]]]}))

(defn about
  [request]
  (page request
     {:title "About Tim"
      :body
      [:div
       [:div.blurb
        [:h1 "Manifesto"]
        [:p "Manifestos are cool.  Everyone knows that.  According to "
         (link-to "http://en.wikipedia.org/wiki/Manifesto" "Wikipedia") ","]
        [:blockquote "A manifesto is a public declaration of principles and intentions, often political in nature. However, manifestos relating to religious belief are generally referred to as a creed. Manifestos may also be life stance-related."]
        [:p "Let's take a look at some of the notable manifestos listed."]
        [:ul
         [:li (link-to "http://en.wikipedia.org/wiki/United_States_Declaration_of_Independence" "United States Declaration of Independence")]
         [:li (link-to "http://en.wikipedia.org/wiki/The_Communist_Manifesto" "The Communist Manifesto")]
         [:li (link-to "http://en.wikisource.org/wiki/Industrial_Society_and_Its_Future" "Industrial Society and Its Future")]
         [:li (link-to "http://en.wikipedia.org/wiki/The_Cathedral_and_the_Bazaar" "The Cathedral and the Bazaar")]
         [:li (link-to "http://www.phrack.org/issues.html?issue=7&id=3&mode=txt" "The Hacker's Manifesto")]
         [:li (link-to "http://en.wikipedia.org/wiki/Agile_Manifesto" "Agile Manifesto (?)")]]
        [:p "Most of these seem to be extreme in both their viewpoints and their word counts.  Neither of which are my style.  I think what I'm after is the  "
         (link-to "http://en.wikipedia.org/wiki/Life_stance" "Life Stance")
         ".  I believe it was Ferris Bueller who said:"]
         [:blockquote "Isms in my opinion are not good. A person should not believe in an ism, he should believe in himself. I quote John Lennon:"
          [:blockquote "I don't believe in Beatles, I just believe in me."] "Good point there.  After all, he was the Walrus."]
        [:p "So that's me.  I don't believe in an Isms, I just believe in myself and, of course, my Life Stance.  That I haven't written yet."]]
       [:div.blurb
        [:h1 "FAQ"]
        [:div.question
         [:p "Do you want to make more money?"]]
        [:div.answer
         [:p "Sure, we all do."]]]]}))

(defn contact
  [request]
  (page
    request
    {:title "Contact Tim"
     :body
     [:div
      [:div.blurb
       [:h1 "Email"]
       [:p "The best way to get ahold of me is through email.  tim [at] [this domain]."]]]}))

(defn work
  [request]
  (page
    request
    {:title "Tim's Work"
     :body
      [:div
       [:div.blurb
        [:h1 "HotPads.com"]
        [:p "My first job"]]
       [:div.blurb
        [:h1 "Synacor, Inc."]
        [:p "My second job"]]
       [:div.blurb
        [:h1 "This Site"]
        [:p "One of the reasons I built this site was to give myself a programming playground.  This site is hosted on Google App Engine.  It is written in Clojure, which is a really cool functional language that runs on the JVM.  Luckily, someone wrote a very nice web application library for Clojure called Compojure.  On the client side I plan on refining my HTML/CSS/JS.  My JavaScript library of choice is JQuery.  And sometimes I add a Flash or Flex app."]]
       [:div.blurb
        [:h1 "Resume"]]]}))
