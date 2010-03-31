(ns tl.pages
  (:use clojure.contrib.ns-utils))

(immigrate
  'tl.pages.about
  'tl.pages.admin
  'tl.pages.contact
  'tl.pages.global
  'tl.pages.home
  'tl.pages.topic
  'tl.pages.work)
