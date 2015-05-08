(ns tl.pages.youtubes
  (:use [clojure.data.json :only [read-json]]
        [hiccup.element :only [link-to]]
        [ring.util.codec :only [url-encode]]
        [tl.db :as db]
        [tl.pages.global :only [pagify]]))

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

(defn youtubes-list []
  (let [videos (reverse (sort-by #(get % "last-seen") (db/youtube-get-all)))
        paras (mapv (fn [vid]
                      (let [id (get vid "video-id")]
                        [:p (link-to id id) " : " (get vid "count")]))
                    videos)]
    (pagify
     {:title "List all played Youtubes"
      :body paras})))

(defn youtubes-watch [video]
  (incr-video-count video)
  {:body "OK"})
