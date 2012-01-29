(ns tl.pages.maps
  (:use [hiccup.page-helpers :only [link-to]]
        [tl.pages.global :only [reduce-blurbs swfobject]]))

(def map-css "/css/maps.css")

(def maps {:google {:css #{"/css/gmap.css" map-css}
                    :js #{"/js/gmap.js"
                          "http://maps.google.com/maps/api/js?sensor=false"}
                    :body [[:div#gmap.map]]
                    :title ["Google"]}
           :mxn {:css #{map-css}
                 :js #{"http://maps.google.com/maps/api/js?sensor=false"
                       "/js/lib/mxn/mxn.js?(googlev3)"
                       "/js/mxn.js"}
                 :body [[:div#mxn.map]]
                 :title ["Mapstraction"]}})

(defn map-links []
  (map #(link-to (name (key %)) (first (:title (val %)))) maps))

(defn map-links-list []
  (vec (concat [:ul] (map #(merge [:li] %) (map-links)))))

(defn summary []
  {:title ["Maps"]
   :body [[:div#maps
           [:h1 "Maps"]
           [:p "A playground for different browser-based maps."]
           (map-links-list)]]})

(defn map-page
  ([] (summary))
  ([kind]
     (if-let [map-type ((keyword kind) maps)]
       (reduce-blurbs (summary) map-type))))
