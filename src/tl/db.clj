(ns tl.db
  (:import (com.google.appengine.api.datastore Query))
  (:require [tl.appengine.datastore :as ds])
  (:use clojure.contrib.str-utils)
  (:use [ring.util.response :only [redirect]])
  (:use tl.util))

(defn- validate* [arg]
  `(when (empty? ~arg)
     (die "You left '" (str '~arg) "' blank.  Try again.")))

(defmacro validate [& args]
  `(do ~@(map validate* args)))

(defn partial-post-from-params
  [params]
  (let [title (params "title")
        markdown (params "markdown")
        tags (params "tags")]
    (validate title markdown tags)
    {:date (java.util.Date.)
     :kind "article"
     :markdown markdown
     :tags tags
     :title title}))

(defn post-from-params [params]
  (let [id (params "id")]
    (validate id)
    (merge {:id id} (partial-post-from-params params))))

(defn add-post
  [post]
  (ds/create post))

(defn edit-post
  [post]
  (ds/update (:id post) post))

(defn do-add-post [params]
  (add-post (partial-post-from-params params))
  (redirect "/admin/"))

(defn do-edit-post [params]
  (edit-post (post-from-params params))
  (redirect "/admin/"))

(defn find-topic
  [topic]
  (ds/find-all (doto (Query. "article"))))

(defn find-all []
  (ds/find-all (doto (Query. "article") (.addSort "date"))))
