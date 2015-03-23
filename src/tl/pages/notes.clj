(ns tl.pages.notes
  (:use [hiccup.element :only [javascript-tag]]
        [hiccup.page :only [include-js]]
        [markdown.core :only [md-to-html-string]]
        [tl.pages.global :only [init-highlight-js pagify]]))

(defn markdown [path-or-file]
  (md-to-html-string (slurp path-or-file)))

(defn all-notes []
  (drop 1 (file-seq (clojure.java.io/file "resources/markdown/notes/"))))

(defn notes-page-slow []
  (pagify
   {:title ["Notes"]
    :js [init-highlight-js]
    :body (mapv markdown (all-notes))}))

(def notes-page (memoize notes-page-slow))
