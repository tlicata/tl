(ns tl.pages.youtubes
  (:use [clojure.data.json :only [read-json]]
        [hiccup.element :only [link-to]]
        [ring.util.codec :only [url-encode]]
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

(defn youtubes-page [video query]
  (pagify (youtubes video query)))