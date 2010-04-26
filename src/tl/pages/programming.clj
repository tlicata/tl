(ns tl.pages.programming
  (:use tl.pages.global))

(defn programming
  [request]
  (page
    request
    {:title "Defraggin' my Hard Drive for Thrills"
     :body
      [:div
       [:div.blurb
        [:h1 "Programming"]
        [:p "Javascript Clojure ActionScript"]]]}))
