(ns tl.pages.photos
  (:use [hiccup.element :only [link-to]]
        [tl.pages.global :only [pagify]]))

(defn in?
  [coll val]
  "Returns true if value is present in the given collection, otherwise
returns false. See also 'contains?'"
  (some #(= val %) coll))

(def pics-base "http://photos.timothylicata.com/")
(def pics-ext ".jpg")
(def pics ["janelle-view"
           "whiteboard-daze-left"
           "omalley-roof"
           "end-of-era"
           "botanical-capitol"
           "hotpads-podium"
           "dart-thru-dart"
           "dora-upside-down"
           "georgetown-floods"
           "starcraft-victory"
           "plane-coming-in"
           "rosslyn-construction"
           "longwood-gardens"
           "google-io-robots"
           "google-io-sergey"
           "bay-to-breakers"
           "sf-pacific-coast"])

(defn photos-nav
  [current]
  (let
      [index (.indexOf pics current)
       previous (when (> index 0) (get pics (- index 1)))
       next (when (< index (- (count pics) 1)) (get pics (+ index 1)))]
    [:ul.pager
     (when-not (nil? previous) [:li (link-to previous "previous")])
     (when-not (nil? next) [:li (link-to next "next")])]))

(defn photos [name]
  (if (or (nil? name) (in? pics name))
    (let [htmlify (fn [name]
                    (when-not (nil? name)
                      [:img {:src (str pics-base name pics-ext)}]))
          thumbnails (fn [] (map (fn [img]
                                   [:div.col-md-6.col-sm-12
                                    [:a.thumbnail {:href img}
                                     (htmlify img)]])
                                 pics))]
      {:title ["Photos"]
       :body [[:div#photos
               (if (nil? name)
                 (thumbnails)
                 [:div
                  (photos-nav name)
                  (htmlify name)])]]})))

(defn photos-page
  ([]
     (pagify (photos nil)))
  ([name]
     (pagify (photos name))))
