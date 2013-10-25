(ns tl.try
  (:require
   [clojure.browser.event :as event]
   [dommy.core :as dommy])
  (:use-macros
   [dommy.macros :only [node sel sel1]]))

(dommy/append! (sel1 :body) (node [:p.food "pizza"]))