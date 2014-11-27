(ns tl.pages.tictactoe
  (:use [hiccup.element :only [javascript-tag]]
        [hiccup.page :only [include-js]]
        [markdown.core :only [md-to-html-string]]
        [tl.pages.global :only [pagify]]))

(defn tictactoe-page []
  (pagify {:title ["Cljs Trial"]
           :body [[:div#ttt
                   (repeat 9 [:div.square])]
                  [:div#ttt-explain
                   (md-to-html-string (slurp "resources/markdown/tictactoe.md"))]
                  (javascript-tag "var CLOSURE_NO_DEPS = true;")
                  (javascript-tag "hljs.initHighlightingOnLoad();")
                  (include-js "/js/bin/all.js")]}))
