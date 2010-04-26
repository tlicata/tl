(ns tl.pages
  (:use clojure.contrib.ns-utils))

(immigrate
  'tl.pages.about
  'tl.pages.admin
  'tl.pages.contact
  'tl.pages.global
  'tl.pages.golf
  'tl.pages.home
  'tl.pages.programming
  'tl.pages.topic
  'tl.pages.work
  'tl.pages.youtubes)
