(ns tl.core
  (:use [compojure.core :only [defroutes GET POST]]
        [ring.middleware.file :only [wrap-file]]
        [ring.middleware.file-info :only [wrap-file-info]]
        [ring.middleware.json :only [wrap-json-response]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.session :only [wrap-session]]
        [tl.jobs :only [jobs-page]]
        [tl.middleware :only [wrap-current-link]]
        [tl.pages.home :only [home]]
        [tl.pages.notes :only [notes-page]]
        [tl.pages.photos :only [photos-page]]
        [tl.pages.tictactoe :only [tictactoe-page]]
        [tl.pages.youtubes :only [youtubes-list youtubes-page youtubes-video youtubes-watch]])
  (:require [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [tl.jobs :as jobs])
  (:gen-class))

(defroutes tl-routes
  (GET "/" [] (home))
  (GET "/jobs/" [] (jobs-page))
  (GET "/notes/" [] (notes-page))
  (GET "/notes/:title" [title] (notes-page title))
  (GET "/photos/" [] (photos-page))
  (GET "/photos/:id" [id] (photos-page id))
  (GET "/tictactoe/" []  (tictactoe-page))
  (GET "/youtubes/" [query] (youtubes-page nil query))
  (GET "/youtubes/list" [cmd] (youtubes-list cmd))
  (POST "/youtubes/video" [cmd] (youtubes-video cmd))
  (GET "/youtubes/:video" [video query] (youtubes-page video query))
  (GET "/youtubes/:video/watch" [video] (youtubes-watch video)))

(defroutes error-routes
  (route/not-found "Not Found"))

(defroutes all-routes
  tl-routes
  error-routes)

(def app
     (-> #'all-routes
         wrap-params
         wrap-session
         (wrap-file "resources/public")
         wrap-file-info
         wrap-json-response
         wrap-current-link))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    (jetty/run-jetty app {:port port})))

(defn dev-main [] (future (-main)))
