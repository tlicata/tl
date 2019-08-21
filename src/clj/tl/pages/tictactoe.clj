(ns tl.pages.tictactoe
  (:use [hiccup.element :only [javascript-tag]]
        [hiccup.page :only [include-js]]
        [tl.pages.global :only [pagify]]))

(defn tictactoe-page-slow []
  (pagify {:title ["Cljs Trial"]
           :body [[:div#ttt (repeat 9 [:div.square])]
                  (javascript-tag "var CLOSURE_NO_DEPS = true;")
                  (include-js "/js/bin/all.js")]}))

(def tictactoe-page (memoize tictactoe-page-slow))
