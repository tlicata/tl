(ns tl.pages.programming
  (:use [tl.pages.global :only [reduce-blurbs page page-full-screen]]))

(def map-css "/css/maps.css")

(defn full-screen? [{uri :uri}] (not (= (. uri indexOf "fullscreen") -1)))
(defn get-kind [request] (get (:route-params request) "kind"))

(def map-blurbs {:polymaps {:css ["/css/poly.css" map-css]
							:js ["/js/poly.js" "/js/polymaps.min.js"]
							:html [[:div#map]]
							:title "Polymaps"}
				 :google {:css ["/css/gmap.css" map-css]
						  :js ["/js/gmap.js" "http://maps.google.com/maps/api/js?sensor=false"]
						  :html [[:div#map]]
						  :title "Google Maps"}})

(defmulti map-page get-kind)
(defmethod map-page :default [request]
		   (let [kind (get-kind request)
				 fullscreen (full-screen? request)
				 extra {:request request
						:kind kind
						:fullscreen fullscreen}
				 blurb (merge extra ((keyword kind) map-blurbs))]
			 (if fullscreen
			   (page-full-screen blurb)
			   (page blurb))))
(defmethod map-page nil [request]
		   (when-not (full-screen? request)
			 (page (reduce-blurbs (vals map-blurbs)))))
