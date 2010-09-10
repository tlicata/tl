(ns tl.pages.programming
  (:use
   [hiccup.page-helpers :only [include-css link-to]]
   [tl.pages.global :only [blurb page]]))

(defn polymaps []
  (page [] {:title "Polymaps"
			:js ["/js/poly.js" "/js/polymaps.min.js"]
			:blurbs [(blurb [:div#pmap])]}))

(defn google []
  (page [] {:title "Google Maps"
			:js ["/js/gmap.js" "http://maps.google.com/maps/api/js?sensor=false"]
			:blurbs [(blurb [:div#gmap])]}))