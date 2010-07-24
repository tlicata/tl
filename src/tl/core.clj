(ns tl.core
  (:use compojure.core
	ring.adapter.jetty
	tl.pages.home)
  (:require [compojure.route :as route]))

(defroutes tl
  (GET "/" [] (home-page {}))
  (route/files "/" {:root "war/public"})
  (ANY "*" [] {:status 404 :body "<h1>404</h1>"}))

(run-jetty (var tl) {:port 8080})