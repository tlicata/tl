(ns tl.pages.home
  (:use [clojure.data.json :only [read-json]]
        [clojure.string :only [join]]
        [clojure.pprint :only [pprint]]
        [hiccup.element :only [link-to javascript-tag]]
        [hiccup.page :only [include-js]]
        [ring.util.response :only [redirect]]
        [tl.pages.global :only [pagify]])
  (:require [hiccup.form :as form]))

(defn home []
  (pagify {:title ["Home"]
           :body [[:div#particles]]
           :js #{"/js/lib/jgestures.js" "/js/lib/three.js" "/js/particles.js?2"}}))

(defn cljs []
  (pagify {:title ["Cljs Trial"]
           :body [[:div#ttt ""] ; tags need content to be bootstrapped
                  (javascript-tag "var CLOSURE_NO_DEPS = true;")
                  (include-js "/js/bin/all.js")]}))
