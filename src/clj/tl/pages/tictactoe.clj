(ns tl.pages.tictactoe
  (:use [hiccup.element :only [javascript-tag]]
        [hiccup.page :only [include-js]]
        [markdown.core :only [md-to-html-string]]
        [tl.pages.global :only [init-highlight-js pagify]]))

(defn tictactoe-page-slow []
  (pagify {:title ["Cljs Trial"]
           :js [init-highlight-js]
           :body [[:div#ttt
                   (repeat 9 [:div.square])]
                  [:div#ttt-explain
                   (md-to-html-string (slurp "resources/markdown/tictactoe.md"))]
                  (javascript-tag "var CLOSURE_NO_DEPS = true;")
                  (include-js "/js/bin/all.js")]}))

(def tictactoe-page (memoize tictactoe-page-slow))
