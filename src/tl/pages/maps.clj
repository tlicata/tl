(ns tl.pages.maps
  (:use [clojure.contrib.string :only [as-str]]
		[hiccup.page-helpers :only [link-to]]
		[tl.pages.global :only [reduce-blurbs swfobject]]))

(def map-css "/css/maps.css")

(def map-blurbs {:polymaps {:css #{"/css/poly.css" map-css}
							:js #{"/js/poly.js" "/js/lib/polymaps.min.js"}
							:body [[:div#pmap.map]]
							:title ["Polymaps"]}
				 :google {:css #{"/css/gmap.css" map-css}
						  :js #{"/js/gmap.js"
							   "http://maps.google.com/maps/api/js?sensor=false"}
						  :body [[:div#gmap.map]]
						  :title ["Google"]}
				 :hotpads {:css #{map-css}
						   :js #{"/js/hotpads.js" swfobject}
						   :body [[:div#mapDiv.map]]
						   :title ["Hotpads"]}})

(defn map-links []
  (map #(link-to (as-str (key %)) (first (:title (val %)))) map-blurbs))

(defn map-links-list []
  (merge [:ul] (map #(merge [:li] %) (map-links))))

(defn map-nav []
  (merge [:ul#nav-secondary] (rest (map-links-list))))

(defn summary-blurb []
  {:title ["Maps"]
   :body [[:div
		   [:h1 "Maps"]
		   [:p "A playground for different browser-based maps."]
		   (map-links-list)]]})

(defn map-page
  ([] (summary-blurb))
  ([kind]
	 (let [kind-blurb ((keyword kind) map-blurbs)]
	   (reduce-blurbs (summary-blurb) kind-blurb))))
