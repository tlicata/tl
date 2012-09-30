(ns tl.try
  (:require [domina :as d]
            [clojure.browser.event :as event]))

(def search-button (d/by-id "search-btn"))

(event/listen search-button "click"
              (fn [] (js/alert (d/value (d/by-id "lname")))))