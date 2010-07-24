(ns tl.core
  (:use compojure.core
	ring.adapter.jetty
	tl.pages.golf
	tl.pages.home
	tl.pages.programming
	tl.pages.youtubes)
  (:require [compojure.route :as route]))

(defroutes tl
  (GET "/" [] (home-page {}))
  (GET "/golf.html" [] (golf {}))
  (GET "/programming.html" [] (programming {}))
  (GET "/youtubes.html" [] (youtubes {}))
  (route/files "/" {:root "war/public"})
  (ANY "*" [] {:status 404 :body "<h1>404</h1>"}))

(run-jetty (var tl) {:port 8080})