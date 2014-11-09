(ns tl.pages.global
  (:use [clojure.string :only [join]]
        [clojure.set :only [union]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [doctype html5 include-css include-js]]))

(def analytics "/js/analytics.js")
(def jquery "/js/lib/jquery-1.7.1.js")
(def main "/css/main.css?5")
(def ubuntu-font "http://fonts.googleapis.com/css?family=Ubuntu")

(defn css [& more]
  (let [global [main ubuntu-font]]
    (apply include-css (concat global more))))

(defn js [& more]
  (let [global [jquery]]
    (apply include-js (concat global more [analytics]))))

(defn reduce-blurbs [& blurbs]
  (reduce #(merge-with union %1 %2) blurbs))

(defn head [title css-arg js-arg]
  `[:head
    [:title ~(join " - " (cons "Tim's Online World" title))]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=yes"}]
    ~@(apply css css-arg)
    ~@(apply js js-arg)])

(defn header-data []
  [{:uri "/" :text "Home"}
   {:uri "/maps/" :text "Maps"}
   {:uri "/photos/" :text "Photos"}
   {:uri "/youtubes/" :text "Youtubes"}])

(defn header-links []
  (let [links (map (fn [{uri :uri text :text}]
                     [:li (link-to uri text)])
                   (header-data))]
    `[:ul.nav ~@links]))

(defn wrap-in-layout [title css js body]
  [:html
   (head title css js)
   [:body (cons (header-links) body)]])

(defn pagify [obj]
  (when-not (nil? obj)
    (let [layout (wrap-in-layout (:title obj)
                                 (:css obj)
                                 (:js obj)
                                 (:body obj))]
      (assoc obj :body (html5 layout)))))
