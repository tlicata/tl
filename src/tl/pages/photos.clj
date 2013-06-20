(ns tl.pages.photos
  (:use [hiccup.element :only [link-to]]
        [tl.pages.global :only [pagify]]))

(defn in?
  [coll val]
  "Returns true if value is present in the given collection, otherwise
returns false. See also 'contains?'"
  (some #(= val %) coll))

(def pics-base "http://dl.dropbox.com/u/2163446/photos/")
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

(defn photos [name]
  (if (or (nil? name) (in? pics name))
    (let [htmlify (fn [name]
                    (when-not (nil? name)
                      [:img {:src (str pics-base name pics-ext)}]))
          all-photos (fn [] (merge [:ul#photos]
                                   (map (fn [img]
                                          [:li [:a {:href img}
                                                (htmlify img)]])
                                        pics)))]
      {:title ["Photos"]
       :body [(if (nil? name)
                (all-photos)
                (htmlify name))]})))

(defn photos-page
  ([]
     (pagify (photos nil)))
  ([name]
     (pagify (photos name))))
