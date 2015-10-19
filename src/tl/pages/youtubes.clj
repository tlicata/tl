(ns tl.pages.youtubes
  (:require [clojure.data.json :refer [read-json]]
            [clojure.string :as string]
            [hiccup.core :refer [html]]
            [hiccup.element :refer [link-to]]
            [clj-http.client :as client]
            [ring.util.codec :refer [url-encode]]
            [tl.db :as db]
            [tl.pages.global :refer [pagify]]))

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


(defn youtubes-playlist [videos]
  (map #(let [id (get % "video-id")]
          {:id id :title (get % "title" id) :count (get % "count")})
       videos))
(defn youtubes-playlist-add [list-name video-id]
  (db/youtube-list-add list-name video-id)
  {:body "OK"})
(defn youtubes-playlist-remove [list-name video-id]
  (db/youtube-list-remove list-name video-id)
  {:body "OK"})
(defn youtubes-playlist-demote [list-name video-id]
  (db/youtube-list-demote list-name video-id)
  {:body "OK"})
(defn youtubes-list [cmd]
  (let [[group action & params] (let [elems (string/split cmd #" ")]
                                  (concat (when (= (count elems) 1) ["list" "show"]) elems))]
    {:body
     (if (= group "list")
       (condp = action
         "show" (youtubes-playlist
                 (let [list-name (first params)]
                   (if (= list-name "history")
                     (reverse (sort-by #(get % "last-seen") (db/youtube-get-all)))
                     (db/youtube-list-show list-name))))
         "add" (apply youtubes-playlist-add params)
         "remove" (apply youtubes-playlist-remove params)
         "demote" (apply youtubes-playlist-demote params)))}))

(defn youtubes-watch [video]
  (incr-video-count video)
  {:body "OK"})

;; When logging videos played on the site, we're only recording
;; IDs. Have a job that runs in the background and downloads the title
;; of each viewed video. The API supports calling for info of up to
;; 50 videos at once.
;; https://developers.google.com/youtube/v3/docs/videos/list
(def youtube-api "https://www.googleapis.com/youtube/v3/videos")
(def youtube-key (System/getenv "YOUTUBE_API_KEY"))
(defn youtubes-lookup-titles []
  (let [lacking-info (take 50 (remove #(contains? % "title") (db/youtube-get-all)))]
    (when-not (empty? lacking-info)
      (let [ids (string/join "," (map #(get % "video-id") lacking-info))
            query {:query-params {:part "snippet" :id ids :key youtube-key}}]
        (println (str "Calling YouTube for info on " ids))
        (try
          (map (fn [itm]
                 [(:id itm) (get-in itm [:snippet :title])])
               (:items (read-json (:body (client/get youtube-api query)))))
          (catch Exception e (str "caught exception: " (.getMessage e))))))))
(defn youtubes-update-titles []
  (if-let [info (youtubes-lookup-titles)]
    (db/youtube-update-title info)))
