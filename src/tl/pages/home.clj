(ns tl.pages.home
  (:use [tl.pages.global :only [pagify]]))

(defn home []
  (pagify {:title ["Home"]
           :body [[:div#particles]]
           :js #{"/js/lib/jgestures.js" "/js/lib/three.js" "/js/particles.js?2"}}))
