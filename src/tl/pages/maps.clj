(ns tl.pages.maps
  (:use [clojure.contrib.string :only [as-str capitalize]]
		[hiccup.page-helpers :only [link-to]]
		[tl.pages.global :only [page page-full-screen]]))

(def map-css "/css/maps.css")

(defn get-kind [request] (get (:route-params request) "kind"))

(def map-blurbs {:polymaps {:css ["/css/poly.css" map-css]
							:js ["/js/poly.js" "/js/polymaps.min.js"]
							:html [[:div#pmap.map]]
							:title "Polymaps"}
				 :google {:css ["/css/gmap.css" map-css]
						  :js ["/js/gmap.js" "http://maps.google.com/maps/api/js?sensor=false"]
						  :html [[:div#gmap.map]]
						  :title "Google Maps"}})

(defn map-links []
  (map #(link-to (as-str (key %)) (:title (val %))) map-blurbs))

(defn map-links-list []
  (merge [:ul] (map #(merge [:li] %) (map-links))))

(defn map-nav []
  (merge [:ul#nav-secondary] (rest (map-links-list))))

(def summary-blurb {:html
					[[:div
					  [:h1 "Maps"]
					  [:p "A playground for different browser-based maps."]
					  (map-links-list)]]})

(defmulti map-page get-kind)
(defmethod map-page :default [request]
		   (let [kind (get-kind request)
				 extra {:request request :kind kind :fullscreen true :nav (map-nav)}
				 blurb (merge extra ((keyword kind) map-blurbs))]
			 (page-full-screen blurb)))
(defmethod map-page nil [request] (page summary-blurb))
