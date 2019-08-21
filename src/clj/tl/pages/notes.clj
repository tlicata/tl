(ns tl.pages.notes
  (:use [hiccup.element :only [javascript-tag link-to]]
        [hiccup.page :only [include-js]]
        [markdown.core :only [md-to-html-string]]
        [tl.pages.global :only [init-highlight-js pagify]]))

(def NOTES_PATH "resources/markdown/notes/")

(defn markdown [path-or-file]
  (md-to-html-string (slurp path-or-file)))

(defn all-notes []
  (drop 1 (file-seq (clojure.java.io/file NOTES_PATH))))

(defn remove-extension [file]
  (clojure.string/replace (.getName file) #".md" ""))

(defn add-extension [filename]
  (str filename ".md"))

(defn linkize [file]
  (remove-extension file))

(defn titleize [file]
  (-> (remove-extension file)
      (clojure.string/replace #"-" " ")
      (clojure.string/capitalize)))

(defn fileize [link]
  (let [links (map linkize (all-notes))]
    (when (some #{link} links)
      (str NOTES_PATH (add-extension link)))))

(defn link-to-title [file]
  (link-to (linkize file) (titleize file)))

(defn notes-page-slow
  ([]
   (pagify
    {:title ["Notes"]
     :body (mapv link-to-title (all-notes))}))
  ([title]
   (when-let [note (fileize title)]
     (pagify
      {:title ["Notes"]
       :js [init-highlight-js]
       :body [(markdown note)]}))))

(def notes-page (memoize notes-page-slow))
