(ns tl.pages.topic
  (:use tl.pages.global))

(defn topic
  ([topic]
   (page {} {:title topic
             :body
             [:div.blurb
              [:h1 topic]
              [:p "topic only"]]}))
  ([topic entry]
   (page {} {:title topic
             :body
             [:div.blurb
              [:h1 topic]
              [:p entry]]})))