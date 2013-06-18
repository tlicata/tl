(ns tl.pages.global
  (:use [clojure.string :only [join]]
        [clojure.set :only [union]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [doctype html5 include-css include-js]]))

(def analytics "/js/analytics.js")
(def jquery "/js/lib/jquery-1.7.1.js")
(def main "/css/main.css?3")

(defn css [& more]
  (let [global [main]]
    (apply include-css (concat global more))))

(defn js [& more]
  (let [global [jquery]]
    (apply include-js (concat global more [analytics]))))

(defn reduce-blurbs [& blurbs]
  (reduce #(merge-with union %1 %2) blurbs))

(defn head [title css-arg js-arg]
  (vec
   (concat
    [:head [:title (join " - " (cons "Tim's Online World" title))]]
    (vec (apply css css-arg))
    (vec (apply js js-arg)))))

(defn wrap-in-layout [title css js body]
  [:html
   (head title css js)
   `[:body ~@body]])

(defn pagify [obj]
  (when-not (nil? obj)
    (let [layout (wrap-in-layout (:title obj)
                                 (:css obj)
                                 (:js obj)
                                 (:body obj))]
      (assoc obj :body (html5 layout)))))
