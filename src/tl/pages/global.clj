(ns tl.pages.global
  (:use [hiccup.core :only [html]]
		[hiccup.page-helpers :only [include-css include-js link-to]]))

(def ajax-api "http://www.google.com/jsapi?key=ABQIAAAAleydYZEjUE7f9RhdHI8NtBS6-BlYLfNinRJgsDPbRk1Y0-Rs-hR5dAJbQkD-YuuQBRVFZP5E1evO4Q")
(def jquery "http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js")
(def grid "http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css")

(defn css [& more]
  (let [global [grid "/css/main.css"]]
	(apply include-css (concat global more))))

(defn js [& more]
  (let [global [ajax-api jquery]]
	(apply include-js (concat global more))))

(defn blurb [html] (conj [:div.blurb] html))

(defn reduce-blurbs [blurbs]
  (reduce #(merge-with concat %1 %2) blurbs))

(defn blurbify [{html :html}]
  (let [blurbs (map blurb html)]
	(apply conj [:div#bd] blurbs)))

(defn head [info]
  [:head
   [:title (str "Tim's Online World - " (:title info))]
   (apply css (:css info))
   (apply js (:js info))])

(defn header [{kind :kind fullscreen :fullscreen}]
  [:div#hd
   [:ul#left-links
	[:li (link-to "google" "Google Maps")]
	[:li (link-to "polymaps" "Polymaps")]]
   (when kind
	 [:ul#right-links
	  (if fullscreen
		[:li (link-to (str "/maps/" kind) "Un-Fullscreen")]
		[:li (link-to (str "/maps/fullscreen/" kind) "Fullscreen")])])])

(defn footer []
  [:div#ft
   [:ul
	[:li (link-to "http://clojure.org" "Clojure")]
	[:li (link-to "http://code.google.com/appengine/" "Google App Engine")]
	[:li (link-to "http://www.crockford.com/javascript/" "Javascript")]]])

(defn page [info]
  (html [:html (head info)
		 [:body [:div#doc4
				 (header info)
				 (apply conj [:div#bd] (map blurb (:html info)))
				 (footer)]]]))

(defn page-full-screen [info]
  (html [:html (head info)
		 [:body [:div#doc3
				 (header info)
				 (conj [:div#bd] (first (:html info)))]]]))
