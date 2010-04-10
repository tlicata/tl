(ns tl.appengine
  (:use clojure.contrib.ns-utils))

(immigrate
  'tl.appengine.datastore
  'tl.appengine.users)
