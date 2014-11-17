(ns tl.core
  (:use [compojure.core :only [defroutes GET]]
        [ring.middleware.file :only [wrap-file]]
        [ring.middleware.file-info :only [wrap-file-info]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.session :only [wrap-session]]
        [tl.pages.home :only [home tictactoe]]
        [tl.pages.maps :only [maps-page]]
        [tl.pages.photos :only [photos-page]]
        [tl.pages.youtubes :only [youtubes-page]])
  (:require [compojure.route :as route]
            [ring.adapter.jetty :as jetty]))

(defroutes tl-routes
  (GET "/" [] (home))
  (GET "/maps/" [] (maps-page))
  (GET "/maps/:kind" [kind] (maps-page kind))
  (GET "/photos/" [] (photos-page))
  (GET "/photos/:id" [id] (photos-page id))
  (GET "/tictactoe/" []  (tictactoe))
  (GET "/youtubes/" [query] (youtubes-page nil query))
  (GET "/youtubes/:video" [video query] (youtubes-page video query)))

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
         wrap-file-info))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (jetty/run-jetty app {:port port})))

(defn dev-main []
  (future
    (jetty/run-jetty app {:port 5000})))
