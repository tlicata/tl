(ns tl.pages.notes
  (:use [hiccup.element :only [javascript-tag]]
        [hiccup.page :only [include-js]]
        [markdown.core :only [md-to-html-string]]
        [tl.pages.global :only [pagify]]))

(defn markdown [path-or-file]
  (md-to-html-string (slurp path-or-file)))

(defn highlight [obj]
  (let [hljs (javascript-tag "hljs.initHighlightingOnLoad();")]
    (assoc obj :body (merge (:body obj) hljs))))

(defn all-notes []
  (drop 1 (file-seq (clojure.java.io/file "resources/markdown/notes/"))))

(defn notes-page-slow []
  (pagify
   (highlight
    {:title ["Notes"]
     :body (mapv markdown (all-notes))})))

(def notes-page (memoize notes-page-slow))
