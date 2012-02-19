(ns tl.core
  (:use
   [compojure.core :only [defroutes DELETE GET POST]]
   [tl.pages.maps :only [map-page]]
   [ring.middleware.file :only [wrap-file]]
   [ring.middleware.file-info :only [wrap-file-info]]
   [ring.middleware.lint :only [wrap-lint]]
   [ring.middleware.params :only [wrap-params]]
   [ring.middleware.session :only [wrap-session]])
  (:require [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [tl.pages.home :as pages]
            [tl.pages.ltcc :as ltcc]
            [tl.middleware :as mw]))

(defroutes tl-routes
  (GET "/" [] (pages/home-page))
  (GET "/contact/" []  (pages/contact-page))
  (GET "/login/" [] (pages/login-page))
  (GET "/ltcc/" []  (ltcc/ltcc-home))
  (GET "/photos/" [] (pages/photos))
  (GET "/photos/:id" [id] (pages/photos id))
  (GET "/youtubes/" [query] (pages/youtubes nil query))
  (GET "/youtubes/:video" [video query] (pages/youtubes video query)))

(defroutes map-routes
  (GET "/maps/" [] (map-page))
  (GET "/maps/:kind" [kind] (map-page kind)))

(defroutes admin-routes
  (GET "/admin/" [] (pages/admin-page))
  (DELETE "/ltcc/" [foo] (ltcc/ltcc-remove foo))
  (POST "/ltcc/" [foo bar] (ltcc/ltcc-add foo bar)))

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
         wrap-session
         mw/wrap-layout
         mw/wrap-current-link
         mw/wrap-html
         (wrap-file "war")
         wrap-file-info
         wrap-lint))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (jetty/run-jetty app {:port port})))

(defn dev-main []
  (future
    (jetty/run-jetty app {:port 8080})))
