(ns tl.pages.admin
  (:use hiccup.form-helpers)
  (:use tl.blog)
  (:use tl.util)
  (:use tl.pages.global))

(defn row? [html-tree]
  (and (coll? html-tree) (= (keyword :tr) (first html-tree))))

(defn odd-row? [indexed]
  (odd? (last indexed)))

(defn add-odd-class [row]
  (let [html (first row)]
    (assoc html 0 :tr.odd)))

(defn make-odd [row]
  (if (odd-row? row)
    (add-odd-class row)
    (first row)))

(defn zebra
  [table]
  (let [rows (filter row? table)
        indexed-coll (partition 2 (interleave rows (iterate inc 1)))]
    [:table (map make-odd indexed-coll)]))

(defn table-header-columns []
  (let [titles ["title" "date" "category" "tags"]]
    (map (fn [title] [:th title]) titles)))

(defn table-header []
  (let [columns (table-header-columns)]
    (vec (conj columns :tr))))

(defn table-posts
  [posts]
  (vec (map (fn [post]
              [:tr
               [:td (:title post)]
               [:td (pretty-date (:date post))]
               [:td (:category post)]
               [:td (:tags post)]]) posts)))

(defn table-rows
  [posts]
  (let [h (table-header)
        p (table-posts posts)]
    (vec (cons h p))))

(defn table
  [posts]
  (let [rows (table-rows posts)]
    (vec (cons :table rows))))

(defn create-post []
  [:div.blurb
   [:h1 "Create a Post"]
   (form-to [:post "/do-add-post"]
            (form-row text-area "markdown" "Post")
            (form-row text-field "category" "Category")
            (form-row text-field "tags" "Tags")
            (submit-row "Submit"))])

(defn edit-a-post []
  (let [posts (find-all)]
    [:div.blurb
     [:h1 "Edit a Post"]
     (zebra (table (find-all)))]))

(defn admin-page
  [request]
  (page
    request
    {:title "Administrative Duties"
     :body
     [:div
      (create-post)
      (edit-a-post)]}))
