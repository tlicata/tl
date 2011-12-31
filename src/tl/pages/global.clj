(ns tl.pages.global
  (:use [clojure.contrib.str-utils :only [str-join]]
        [clojure.set :only [union]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [doctype include-css include-js link-to]]))

(def analytics "/js/analytics.js")
(def jquery "/js/lib/jquery.min.js")
(def swfobject "/js/lib/swfobject.js")
(def grid "/css/lib/reset-fonts-grids.css")

(defn css [& more]
  (let [global [grid "/css/main.css"]]
    (apply include-css (concat global more))))

(defn js [& more]
  (let [global [jquery]]
    (apply include-js (concat global more [analytics]))))

(defn blurb [html]
  "Wrap in an blurb div. Unless it's a script tag"
  (if (or
       (= nil html)
       (= :script (first html)))
    html
    (conj [:div.blurb] html)))

(defn reduce-blurbs [& blurbs]
  (reduce #(merge-with union %1 %2) blurbs))

(defn blurbify [{html :html}]
  (let [blurbs (map blurb html)]
    (apply conj [:div#bd] blurbs)))

(defn head [title css-arg js-arg]
  (vec
   (concat
    [:head [:title (str-join " - " (cons "Tim's Online World" title))]]
    (vec (apply css css-arg))
    (vec (apply js js-arg)))))

(defn header-data []
  [{:uri "/" :text "Home"}
   {:uri "/maps/" :text "Maps"}
   {:uri "/photos/" :text "Photos"}
   {:uri "/youtubes/" :text "Youtubes"}
   {:uri "/contact/" :text "Contact"}])

(defn header-links []
  (vec
   (concat
    [:ul#nav-primary]
    (map (fn [{uri :uri text :text}]
           [:li (link-to uri text)])
         (header-data)))))

(defn header []
  (let [primary (header-links)
        hd (merge [:div#hd] primary)]
    hd))

(defn footer []
  [:div#ft
   [:p "Functions in"]
   [:ul
    [:li (link-to "http://clojure.org/state" "Clojure") " and"]
    [:li (link-to "http://www.crockford.com/javascript/"
                  "Javascript")]]])

(defn wrap-in-layout [title css js body]
  [:html
   (head title css js)
   [:body
    [:div#doc4
     (header)
     (vec (concat [:div#bd] (map blurb body)))
     (footer)]]])

(defn convert-to-html [body]
  (html (doctype :xhtml-strict) body))
