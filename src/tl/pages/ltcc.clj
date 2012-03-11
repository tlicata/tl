(ns tl.pages.ltcc
  (:use [noir.core :only [defpage]]
        [tl.pages.global :only [pagify]]))

(defpage "/ltcc/" []
  (pagify
   {:title ["Ltcc"]
    :body [[:p "Ltcc"]]
    :js ["/js/bin/all.min.js"]}))
