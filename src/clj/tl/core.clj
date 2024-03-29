(ns tl.core
  (:use [compojure.core :only [defroutes GET POST]]
        [ring.middleware.file :only [wrap-file]]
        [ring.middleware.file-info :only [wrap-file-info]]
        [ring.middleware.json :only [wrap-json-response]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.session :only [wrap-session]]
        [ring.middleware.ssl :only [wrap-hsts wrap-forwarded-scheme wrap-ssl-redirect]]
        [tl.jobs :only [jobs-page]]
        [tl.middleware :only [wrap-current-link]]
        [tl.pages.home :only [home]]
        [tl.pages.notes :only [notes-page]]
        [tl.pages.tictactoe :only [tictactoe-page]]
        [tl.pages.youtubes :only [youtubes-list
                                  youtubes-page
                                  youtubes-skip
                                  youtubes-video
                                  youtubes-watch]])
  (:require [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [tl.jobs :as jobs])
  (:gen-class))

(defroutes tl-routes
  (GET "/" [] (home))
  (GET "/jobs/" [] (jobs-page))
  (GET "/notes/" [] (notes-page))
  (GET "/notes/:title" [title] (notes-page title))
  (GET "/tictactoe/" []  (tictactoe-page))
  (GET "/youtubes/" [query] (youtubes-page nil query))
  (GET "/youtubes/list" [cmd] (youtubes-list cmd))
  (POST "/youtubes/video" [cmd] (youtubes-video cmd))
  (GET "/youtubes/:video" [video query] (youtubes-page video query))
  (GET "/youtubes/:video/watch" [video] (youtubes-watch video))
  (POST "/youtubes/:video/skip" [video skip] (youtubes-skip video skip)))

(defroutes error-routes
  (route/not-found "Not Found"))

(defroutes all-routes
  tl-routes
  error-routes)

(defn get-port []
  (Integer/parseInt (or (System/getenv "PORT") "5000")))

(def app
     (-> #'all-routes
         wrap-params
         wrap-hsts
         wrap-ssl-redirect
         wrap-forwarded-scheme
         wrap-session
         (wrap-file "resources/public")
         wrap-file-info
         wrap-json-response
         wrap-current-link))

(defn -main []
  (jetty/run-jetty app {:port (get-port)}))

(defn dev-main [] (future (-main)))
