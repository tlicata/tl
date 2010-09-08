(ns tl.pages.programming
  (:use
   [hiccup.page-helpers :only [include-css link-to]]
   [tl.pages.global :only [blurb page]]))

(defn programming
  [request]
  (page
    request
    {:title "Programming"
	 :js ["/public/js/polymaps.min.js" "/public/js/poly.js"]
     :body
      [:div
       (blurb
        [:h1 "Polymaps"]
		[:div#polymaps-container])]}))