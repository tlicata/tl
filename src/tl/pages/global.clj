(ns tl.pages.global
  (:use [clojure.string :only [join]]
        [clojure.set :only [union]]
        [hiccup.core :only [html]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [doctype html5 include-css include-js]]))

(def analytics "/js/analytics.js")
(def bootstrap-css "/css/lib/bootstrap.css")
(def bootstrap-css-responsive "/css/lib/bootstrap-responsive.css")
(def bootstrap-js "/js/lib/bootstrap.js")
(def jquery "/js/lib/jquery-1.7.1.js")
(def main "/css/main.css?1")

(defn css [& more]
  (let [global [bootstrap-css main]]
    (apply include-css (concat global more))))

(defn js [& more]
  (let [global [jquery bootstrap-js]]
    (apply include-js (concat global more [analytics]))))

(defn blurb [html]
  "Wrap in an blurb div. Unless it's a script tag"
  (if (or
       (= nil html)
       (= :script (first html)))
    html
    [:div.container (conj [:div.row.well] html)]))

(defn reduce-blurbs [& blurbs]
  (reduce #(merge-with union %1 %2) blurbs))

(defn blurbify [{html :html}]
  (let [blurbs (map blurb html)]
    (apply conj [:div#bd] blurbs)))

(defn head [title css-arg js-arg]
  (vec
   (concat
    [:head [:title (join " - " (cons "Tim's Online World" title))]]
    (vec (apply css css-arg))
    (vec (apply js js-arg)))))

(defn header-data []
  [{:uri "/" :text "Home"}
   {:uri "/maps/" :text "Maps"}
   {:uri "/photos/" :text "Photos"}
   {:uri "/youtubes/" :text "Youtubes"}])

(defn header-links []
  (vec
   (concat
    [:ul.nav.nav-pills]
    (map (fn [{uri :uri text :text}]
           [:li (link-to uri text)])
         (header-data)))))

(defn header []
  (let [primary (header-links)]
    [:div.navbar.navbar-fixed-top
     [:div.navbar-inner
      (merge [:div.container] primary)]]))

(defn wrap-in-layout [title css js body]
  [:html
   (head title css js)
   [:body
    (cons (header) (map blurb body))]])

(defn pagify [obj]
  (when-not (nil? obj)
    (let [layout (wrap-in-layout (:title obj)
                                 (:css obj)
                                 (:js obj)
                                 (:body obj))]
      (assoc obj :body (html (html5 layout))))))
