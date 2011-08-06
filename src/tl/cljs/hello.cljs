(ns tl.cljs.hello
  (:require [clojure.string :as string]
            [goog.net.Jsonp :as jsonp]
            [goog.Timer :as timer]
            [goog.events :as events]
            [goog.events.EventType :as event-type]
            [goog.dom.classes :as classes]
            [goog.dom :as dom]))

(defn dom-element
  "Create a dom element using a keyword for the element name and a map
  for the attributes."
  [element attrs]
  (dom/createDom (name element)
                 (.strobj (reduce (fn [m [k v]]
                                    (assoc m k v))
                                  {}
                                  (map
								    #(vector (name %1) %2)
								    (keys attrs)
									(vals attrs))))))

(defn ^{:export greet} greet [n]
  (js/alert (str "Hello " n)))
