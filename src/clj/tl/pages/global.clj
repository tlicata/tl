(ns tl.pages.global
  (:use [clojure.string :only [join]]
        [clojure.set :only [union]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [doctype html5 include-css include-js]]))

(def analytics "/js/analytics.js")
(def bootstrap-css "/css/lib/theme/cyborg/bootstrap.css")
(def bootstrap-js "/js/lib/bootstrap.js")
(def highlight-css "/css/lib/highlight/solarized_dark.css")
(def highlight-js "/js/lib/highlight.pack.js")
(def init-highlight-js "/js/highlight.js")
(def jquery "/js/lib/jquery-1.11.1.js")
(def main "/css/main.css?6")

(def ^:dynamic *request* {})

(defn css [& more]
  (let [global [bootstrap-css highlight-css main]]
    (apply include-css (concat global more))))

(defn js [& more]
  (let [global [jquery bootstrap-js highlight-js]]
    (apply include-js (concat global more [analytics]))))

(defn blurb [html]
  "Wrap in an blurb div. Unless it's a script tag"
  (if (or
       (empty? (rest html))
       (= :script (first html)))
    html
    [:div.container
     [:div.row
      (conj [:div.col-md-8.col-md-offset-2] html)]]))

(defn reduce-blurbs [& blurbs]
  (reduce #(merge-with union %1 %2) blurbs))

(defn blurbify [{html :html}]
  (let [blurbs (map blurb html)]
    (apply conj [:div#bd] blurbs)))

(defn head [title css-arg js-arg]
  `[:head
    [:title ~(join " - " (cons "Tim's Online World" title))]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=no"}]
    ~@(apply css css-arg)
    ~@(apply js js-arg)])

(defn header-data []
  [;; {:uri "/notes/" :text "Notes"}
   ;; {:uri "/youtubes/" :text "Youtubes"}
   ])

(defn header-links []
  (let [links (map (fn [{uri :uri text :text}]
                     (if (= uri (:uri *request*))
                       [:li.current (link-to uri text)]
                       [:li (link-to uri text)]))
                   (header-data))]
    `[:ul.nav.navbar-nav ~@links]))

(defn header []
  (let [primary (header-links)]
    [:nav.navbar.navbar-default.navbar-fixed-top {:role "navigation"}
     [:div.container
      [:div.navbar-header
       [:button.navbar-toggle.collapsed
        {:data-target "#navbar" :data-toggle "collapse"}
        [:span.sr-only "Toggle navigation"]
        [:span.icon-bar] [:span.icon-bar] [:span.icon-bar]]
       [:a.navbar-brand {:href "/"} "Tim's Online World"]]
      [:div#navbar.navbar-collapse.collapse primary]]]))

(defn wrap-in-layout [{:keys [title css js body]}]
  [:html
   (head title css js)
   [:body
    (cons (header) (map blurb body))]])

(defn pagify [obj]
  (when-not (nil? obj)
    (html5 (wrap-in-layout obj))))
