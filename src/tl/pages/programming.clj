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
     :blurbs [(blurb [:div#polymaps-container])]}))

(defn google []
  (page [] {:title "Google Maps"
			:js ["/js/gmap.js" "http://maps.google.com/maps/api/js?sensor=false"]
			:blurbs [(blurb [:div#gmap])]}))