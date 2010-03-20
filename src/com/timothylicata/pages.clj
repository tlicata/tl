(ns com.timothylicata.pages
  "Shortcut to include all com.timothylicata.pages.* namespaces."
  (:use compojure.ns-utils))

(immigrate
  'com.timothylicata.pages.about
  'com.timothylicata.pages.admin
  'com.timothylicata.pages.contact
  'com.timothylicata.pages.global
  'com.timothylicata.pages.home
  'com.timothylicata.pages.work)
