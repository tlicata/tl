(ns com.timothylicata.blog
  (:import (com.google.appengine.api.datastore Query))
  (:require [appengine.datastore :as ds])
  (:use compojure.html))

(defn post-page
  [id]
  (html
    [:html
     [:h1 "Post id " id]]))

(defn tag-page
  [tag]
  (html
    [:html
     [:h1 "Tag " tag]]))

(defn add-post-page
  []
  (html
    [:html
     [:h1 "Add a post"]]))

(defn edit-post-page
  [id]
  (html
    [:html
     [:h1 "Edit post" id]]))

(defn create
  [content author tags]
  (ds/create {:kind "article" :author author :content content :tags tags :date (java.util.Date.)}))

(defn find-all []
  (ds/find-all (doto (Query. "article") (.addSort "date"))))
