(ns tl.pages.youtubes
  (:use [clojure.data.json :only [read-json]]
        [hiccup.element :only [link-to]]
        [ring.util.codec :only [url-encode]]
        [tl.db :as db]
        [tl.pages.global :only [pagify]]))

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
  (vec
   (cons :div#youtubes-search
         [[:div#search-results
           (if (empty? results)
             [:span "Go fish."]
             (map (fn [entry]
                    [:div.entry
                     (link-to (:id entry) (:title entry))
                     [:span.viewed (str (:viewed entry) " views")]])
                  results))]])))

(defn incr-video-count [video]
  (when video (db/youtube-played video)))

(defn youtubes [video query]
  (let [video-html [:div#youtubes [:div#player]]
        search-html [:div#youtubes-search
                     [:form.form-search {:method "get"}
                      [:div.input-group
                       [:input.form-control {:type "search" :name "query" :autofocus true :autosave true}]
                       [:span.input-group-btn
                        [:input.btn.btn-primary {:type "submit" :value "Search YouTube"}]]]]]
        iframe-api [:script {:src "https://www.youtube.com/iframe_api"}]
        video-id [:script (str "var video = \"" video "\";")]]
    (incr-video-count video)
    {:js #{"/js/youtubes.js?6"}
     :title ["Hello Youtubes"]
     :body (if video
             [video-html search-html iframe-api video-id]
             [search-html])}))

(defn youtubes-page [video query]
  (pagify (youtubes video query)))

(defn youtubes-watch [video]
  (incr-video-count video)
  {:body "OK"})
