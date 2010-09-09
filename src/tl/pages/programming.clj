(ns tl.pages.programming
  (:use
   [hiccup.page-helpers :only [include-css link-to]]
   [tl.pages.global :only [blurb page]]))

(defn programming
  [request]
  (page
    request
    {:title "Programming"
	 :js ["/js/polymaps.min.js" "/js/poly.js"]
     :body
      [:div
       (blurb
        [:h1 "Polymaps"]
		[:div#polymaps-container])]}))