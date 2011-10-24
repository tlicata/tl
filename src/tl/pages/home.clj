(ns tl.pages.home
  (:use [clojure.contrib.str-utils :only [str-join]]
        [clojure.contrib.json :only [read-json]]
        [hiccup.page-helpers :only [link-to]]
        [ring.util.codec :only [url-encode]]
        [tl.pages.global :only [swfobject]])
  (:require [appengine-magic.services.user :as us]
            [appengine-magic.services.url-fetch :as url]
            [com.reasonr.scriptjure :as script]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
                                          "Programmer"
                                          "( Golfer | Ping Ponger | Shuffler )"
                                          "( Lockport, NY | San Francisco, CA )"])])

(defn home-page []
  {:title ["Home"]
   :body [welcome-blurb]})

(defn contact-page []
  {:title ["Contact"]
   :body [[:div [:p "You can write to me at tim at this domain name."]]]})

(defn admin-page []
  {:title ["Admin"]
   :body [[:div
           [:p "You're an admin baby"]
           (link-to (us/logout-url) "Log out")]]})

(defn login-page []
  {:title ["Login"]
   :body [[:div [:p (if (us/user-logged-in?)
                      (link-to (us/logout-url) "Log out")
                      (link-to (us/login-url) "Log in"))]]]})

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
           "rosslyn-apt"
           "plane-coming-in"
           "rosslyn-construction"
           "longwood-gardens"
           "google-io-robots"
           "google-io-sergey"
           "bay-to-breakers"
           "sf-pacific-coast"])

(defn in?
  [coll val]
  "Returns true if value is present in the given collection, otherwise
returns false. See also 'contains?'"
  (some #(= val %) coll))

(defn photos-nav
  ([]
     (photos-nav nil))
  ([current]
     (let
         [index (.indexOf pics current)
          previous (when (> index 0) (get pics (- index 1)))
          next (when (< index (- (count pics) 1)) (get pics (+ index 1)))]
       [:div#photos-nav
        (link-to (first pics) "first")
        (if (nil? previous) "previous" (link-to previous "previous"))
        (if (nil? next) "next" (link-to next "next"))
        (link-to (last pics) "latest")])))
           
(defn photos
  ([]
     (photos nil))
  ([name]
     (if (or (nil? name) (in? pics name))
       (let [htmlify (fn [name]
                       (when-not (nil? name)
                         [:img {:style "display:block;margin:auto;"
                                :src (str pics-base name pics-ext)}]))]
             {:title ["Photos"]
              :body [[:div
                      (photos-nav name)
                      (htmlify name)]]}))))

(def videos [{:title "Ronald Jenkees - Stay Crunchy" :id "lg8LfoyDFUM"}
             {:title "Ronald Jenkees - All of my Love" :id "j-ryKQx4DdQ"}
             {:title "Ronald Jenkees - Laid Back Organ Jam (for my peeps)" :id "zuJW7H08HrQ"}
             {:title "Tragically Hip - New Orleans is Sinking (w/ Ahead by a Century)" :id "AZwm_OKh6bw"}
             {:title "Tragically Hip - Fully Completely" :id "pEGyKECUh80"}
             {:title "Tragically Hip - 38 Years Old" :id "rsj9fXH2Psw"}
             {:title "Ratatat - Seventeen Years" :id "z6GbC2F5RfU"}
             {:title "Ratatat - Mi Viejo + Mirando" :id "oM3MdixTdfA"}
             {:title "Kid Cudi ft. Ratatat - Pursuit of Happiness" :id "Sd-StlEkSvw"}
             {:title "Handsome Jack - 5-1-08 Camera 1 (and only)" :id "4tc_GxVtcPE"}
             {:title "Handsome Jack - last 1/2 of Love Machine" :id "5QFnWu0WRnY"}
             {:title "Neil Young - Heart of Gold" :id "Eh44QPT1mPE"}
             {:title "Neil Young & Pearl Jam" :id "PTTsyk-pyd8"}
             {:title "Rush - Subdivisions" :id "DNoMCh6okuo"}
             {:title "Rush - Tom Sawyer (featuring South Park)" :id "JFGVDWc_5Q8"}
             {:title "Rush - Workin Man" :id "EYRYGr9vynw"}
             {:title "Mike V - Skates Lockport" :id "xxBdPUokZHo"}
             {:title "Cake - Short Skirt, Long Jacket" :id "cBYEVnQkMU8"}
             {:title "Cake - The Distance" :id "qKax7euEM5Q"}
             {:title "Sublime - Jailhouse" :id "1hVoo75XYqc"}
             {:title "Mumford & Sons - The Cave" :id "YKe33jxDMkQ"}
             {:title "John Frusciante - Tiny Dancer" :id "oU4-r50c1ag"}
             {:title "John Frusciante - Emily" :id "WCmrKgjRb1c"}
             {:title "John Frusciante - Central (The Empyrean)" :id "M9jsRZzjrgI"}])

(def embed-url "http://www.youtube.com/v/")
(def link-url "http://www.youtube.com/watch?v=")
(def search-url "http://gdata.youtube.com/feeds/api/videos")

(defn youtube-list []
  (vec (conj (map (fn [v]
                    [:li (link-to (:id v) (:title v))])
                  videos)
             :ul)))

(defn youtube-search-fetch [query]
  (let [url (str search-url "?q=" (url-encode query) "&alt=json")]
    (try (url/fetch url) (catch Error _ nil))))

(defn youtube-search-parse [response]
  (let [bytes (:content response)
        chars (if bytes (String. bytes) "{}")
        tree (try (read-json chars) (catch Exception _ {}))
        entries (:entry (:feed tree))]
    (map (fn [entry]
           {:author (:$t (:name (first (:author entry))))
            :content (:$t (:content entry))
            :id (re-find #"[^/]*$" (:$t (:id entry)))
            :title (:$t (:title entry))
            :viewed (:viewCount (:yt$statistics entry))})
         entries)))

(defn youtube-search-render [results]
  (if (empty? results)
    [:p "Go fish."]
    (vec (cons :div.search-results
               (map (fn [entry]
                      [:div.entry
                       (link-to (:id entry) (:title entry))
                       [:span.viewed (str (:viewed entry) " views")]])
                    results)))))

(defn youtubes [video query]
  {:js #{"/js/youtubes.js" swfobject}
   :title ["Hello Youtubes"]
   :body [[:div#youtubes
           [:div.left (youtube-list)]
           [:div.right [:div#swf (when video "Playing videos requires JavaScript and Flash.  Either you are missing one of those technologies or my site is broken.  Bummer either way.")]]]
          [:div#youtubes-search
           [:form {:method "get"}
            [:input {:type "text" :name "query"}]
            [:input {:type "submit" :value "Search YouTube"}]]
           (when query
             (youtube-search-render
              (youtube-search-parse
               (youtube-search-fetch query))))]
          (when video
            [:script
             (script/js (tl.youtubes.play (script/clj (str embed-url video))
                                          (script/clj 1)
                                          (script/clj 1)))])]})
