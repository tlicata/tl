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

(defn- include-clojurescript [path]
  (list
   (javascript-tag "var CLOSURE_NO_DEPS = true;")
   (include-js path)))

(defn cljs []
  (pagify {:title ["Cljs Trial"]
           :body [[:div
                   [:input#lname {:type "text" :placeholder "place holder"}]
                   [:input#search-btn {:type "button" :value "Get Stats!"}]]
                  (include-clojurescript "/js/bin/all.js")]}))
