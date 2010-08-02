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
  (GET "/golf.html" [] (golf))
  (POST "/golf.html" [name] (golf-post name))
  (GET "/programming.html" [] (programming {}))
  (GET "/youtubes.html" [] (youtubes {}))
  (route/files "/" {:root "war/public"})
  (route/not-found "Not Found"))

(run-jetty (var tl) {:port 8080})