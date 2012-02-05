(ns tl.golfers
  (:require [goog.net.Jsonp :as jsonp]
            [goog.dom :as dom]))

(defn ^{:export tl.dom} dom []
  (let [new-header (dom/createDom "h1" {} "Hello world!")]
    (dom/appendChild (.-body (dom/getDocument)) new-header)))

(defn ^{:export tl.greet} greet []
  (js/alert "Hello world!"))
