(ns tl.pages.work
  (:use tl.pages.global))

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
