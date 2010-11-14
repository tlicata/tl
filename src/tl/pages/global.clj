(ns tl.pages.global
  (:use [hiccup.core :only [html]]
		[hiccup.page-helpers :only [include-css include-js link-to]]))

(def jquery "/js/lib/jquery.min.js")
(def swfobject "/js/lib/swfobject.js")
(def grid "/js/lib/reset-fonts-grids.css")

(defn css [& more]
  (let [global [grid "/css/main.css"]]
	(apply include-css (concat global more))))

(defn js [& more]
  (let [global [jquery]]
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

(defn header-data []
  [{:uri "/" :text "Home"}
   {:uri "/maps/" :text "Maps"}
   {:uri "/contact/" :text "Contact"}])

(defn header-links [request]
  [:ul#nav-primary
   (map (fn [{uri :uri text :text}]
		  (if (= uri (:uri request))
			[:li.current text]
			[:li (link-to uri text)]))
		(header-data))])

(defn header [{secondary :nav request :request}]
  (let [primary (header-links request)
		hd (merge [:div#hd] primary)]
	(if secondary
	  (merge hd secondary)
	  hd)))

(defn footer []
  [:div#ft
   [:p "Powered by"]
   [:ul
	[:li (link-to "http://clojure.org/state" "Clojure") ","]
	[:li (link-to "http://www.crockford.com/javascript/"
				  "Javascript") ","]
	[:li "and " (link-to "http://thesixtyone.com" "T61") "."]]])

(defn page [info]
  (html [:html (head info)
		 [:body [:div#doc4
				 (header info)
				 (if (:html info)
				   (apply conj [:div#bd] (map blurb (:html info))))
				 (footer)]]]))

(defn page-full-screen [info]
  (html [:html (head info)
		 [:body [:div#doc3
				 (header info)
				 (conj [:div#bd] (first (:html info)))]]]))
