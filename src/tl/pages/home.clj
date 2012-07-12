(ns tl.pages.home
  (:use [clojure.data.json :only [read-json]]
        [clojure.string :only [join]]
        [clojure.pprint :only [pprint]]
        [noir.core :only [defpage]]
        [hiccup.page-helpers :only [link-to]]
        [ring.util.codec :only [url-encode]]
        [tl.pages.global :only [pagify]])
  (:require [com.reasonr.scriptjure :as script]
            [hiccup.form-helpers :as form]
            [noir.response :as resp]
            [tl.user :as user]))

(def welcome-blurb [:span (join "  -  " ["Tim Licata"
                                      "Programmer"
                                      "( Golfer | Ping Ponger | Shuffler )"
                                      "( Lockport, NY | San Francisco, CA )"])])

(defpage "/" []
  (pagify {:title ["Home"]
           :body [welcome-blurb]}))

(defpage "/admin/" request
  (pagify {:title ["Admin"]
           :body [[:div [:p "Admin"]]
                  [:div (link-to "/logout/" "log out")]]}))

(defn login-form []
  (form/form-to [:post "/login/"]
                (form/text-field :username)
                (form/password-field :password)
                (form/submit-button "login")))

(defpage "/login/" []
  (if (user/admin?)
    (resp/redirect "/admin/")
    (pagify {:title ["Login"]
             :body [(login-form)]})))

(defpage [:post "/login/"] {:as user}
  (if (user/login! user)
    (resp/redirect "/admin/")
    (pagify {:title ["Login"]
             :body [[:p "invalid login attempt"]
                    (login-form)]})))

(defpage "/logout/" []
  (do
    (user/logout!)
    (resp/redirect "/login/")))

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
        [:ul
         [:li (link-to (first pics) "first")]
         [:li (if (nil? previous) "previous" (link-to previous "previous"))]
         [:li (if (nil? next) "next" (link-to next "next"))]
         [:li (link-to (last pics) "latest")]]])))

(defn photos [name]
  (if (or (nil? name) (in? pics name))
    (let [htmlify (fn [name]
                    (when-not (nil? name)
                      [:img {:src (str pics-base name pics-ext)}]))]
      {:title ["Photos"]
       :body [[:div#photos
               (photos-nav name)
               (htmlify name)]]})))

(defpage "/photos/" [] (fn [_] (pagify (photos nil))))
(defpage "/photos/:name" {name :name} (fn [_] (pagify (photos name))))

(def link-url "http://www.youtube.com/watch?v=")
(def search-url "http://gdata.youtube.com/feeds/api/videos")

;; Used to use appengine-magic to fetch a url within
;; the appengine restrictions.  Should replace with
;; another library like clj-http.
(defn youtube-search-fetch [query]
  (let [url (str search-url "?q=" (url-encode query) "&alt=json")]
    {:reponse nil}))

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
  (let [url (str "http://youtube.com/embed/" video
                 "?fs=1&autoplay=1&loop=1&playlist=" video)
        video-html (when video [:div#youtubes
                                [:iframe.youtube-player {:frameborder "0"
                                                         :height "420px"
                                                         :src url
                                                         :type "text/html"
                                                         :width "700px"}]])
        search-html [:div#youtubes-search
                     [:form.form-search {:method "get"}
                      [:input.search-query {:type "text" :name "query" :autofocus "true"}]
                      [:input.btn {:type "submit" :value "Search YouTube"}]]
                     (when query
                       (youtube-search-render
                        (youtube-search-parse
                         (youtube-search-fetch query))))]]
        {:js #{"/js/youtubes.js?2"}
         :title ["Hello Youtubes"]
         :body [video-html search-html]}))

(defpage "/youtubes/" {query :query} (pagify (youtubes nil query)))
(defpage "/youtubes/:video" {:keys [video query]}
  (pagify (youtubes video query)))

