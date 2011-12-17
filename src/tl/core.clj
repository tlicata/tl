(ns tl.core
  (:use
   [compojure.core :only [defroutes GET]]
   [tl.pages
    [home :only [admin-page home-page contact-page login-page photos youtubes]]
    [maps :only [map-page]]]
   [ring.middleware.file :only [wrap-file]]
   [ring.middleware.lint :only [wrap-lint]]
   [ring.middleware.params :only [wrap-params]])
  (:require [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [tl.middleware :as mw]))

(defroutes tl-routes
  (GET "/" [] (home-page))
  (GET "/contact/" []  (contact-page))
  (GET "/login/" [] (login-page))
  (GET "/photos/" [] (photos))
  (GET "/photos/:id" [id] (photos id))
  (GET "/youtubes/" [query] (youtubes nil query))
  (GET "/youtubes/:video" [video query] (youtubes video query)))

(defroutes map-routes
  (GET "/maps/" [] (map-page))
  (GET "/maps/:kind" [kind] (map-page kind)))

(defroutes admin-routes
  (GET "/admin/" [] (admin-page)))

(defroutes error-routes
  (route/not-found "Not Found"))

(defroutes all-routes
  tl-routes
  map-routes
  (-> #'admin-routes
      mw/wrap-admin)
  error-routes)

(def app
     (-> #'all-routes
         wrap-params
         mw/wrap-layout
         mw/wrap-current-link
         mw/wrap-html
         (wrap-file "war")
         wrap-lint))

(defn -main []
  (jetty/run-jetty app {:port 5000}))
