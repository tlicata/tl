(ns tl.pages.home
  (:use [clojure.data.json :only [read-json]]
        [clojure.string :only [join]]
        [clojure.pprint :only [pprint]]
        [hiccup.element :only [link-to javascript-tag]]
        [hiccup.page :only [include-js]]
        [markdown.core :only [md-to-html-string]]
        [ring.util.response :only [redirect]]
        [tl.pages.global :only [pagify]])
  (:require [hiccup.form :as form]))

(defn home []
  (pagify {:title ["Home"]
           :body [[:div#particles]]
           :js #{"/js/lib/jgestures.js" "/js/lib/three.js" "/js/particles.js?2"}}))

(defn tictactoe []
  (let [square [:div.square.col-xs-4.col-sm-3.col-md-2]
        offset [:div.square.col-xs-4.col-sm-3.col-md-2.col-sm-offset-1.col-md-offset-3]]
    (pagify {:title ["Cljs Trial"]
             :body [[:div#ttt
                     [:div.row offset square square]
                     [:div.row offset square square]
                     [:div.row offset square square]]
                    [:div#ttt-explain
                     (md-to-html-string (slurp "resources/markdown/tictactoe.md"))]
                    (javascript-tag "var CLOSURE_NO_DEPS = true;")
                    (include-js "/js/bin/all.js")]})))
