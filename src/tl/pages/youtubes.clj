(ns tl.pages.youtubes
  (:use tl.pages.global))

(defn youtubes
  [request]
  (page
    request
    {:title "Hello Youtubes!"
     :body
      [:div
       [:div.blurb
        [:h1 "Hello Youtubes!"]
        [:p "Player and Songs and Rock 'N' Roll"]]]}))
