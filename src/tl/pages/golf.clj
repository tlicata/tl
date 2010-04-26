(ns tl.pages.golf
  (:use tl.pages.global))

(defn golf
  [request]
  (page
    request
    {:title "Golf"
     :body
      [:div
       [:div.blurb
        [:h1 "Golf"]
        [:p "Tee to green."]]]}))
