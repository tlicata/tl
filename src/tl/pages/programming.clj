(ns tl.pages.programming
  (:use
   [hiccup.core :only [html]]
   [hiccup.page-helpers :only [include-css link-to]]
   [tl.pages.global :only [grid js]]))

(def polymaps-blurb {:css ["/css/poly.css"]
					 :js ["/js/poly.js" "/js/polymaps.min.js"]
					 :html [:div#map]})
(def google-blurb {:css ["/css/gmap.css"]
				   :js ["/js/gmap.js" "http://maps.google.com/maps/api/js?sensor=false"]
				   :html [:div#map]})

(defn css [& more]
  (let [default ["/css/maps.css" grid]]
	(apply include-css (concat default more))))

(defn header []
  [:div#hd
   (link-to "/googlemaps.html" "Google Maps")
   (link-to "/polymaps.html" "Polymaps")])

(defn maps-page
  [info]
  (html
   [:html
	[:head
	 [:title (str "Tim's Online World - " (:title info))]
	 (apply css (:css info))
	 (js (:js info))]
	[:body
	 [:div#doc3 (header) (:html info)]]]))

(defn polymaps []
  (maps-page (assoc polymaps-blurb :title "Polymaps")))
(defn google []
  (maps-page (assoc google-blurb :title "Google Maps")))
