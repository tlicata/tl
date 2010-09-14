(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use
   [compojure.core :only [defroutes GET]]
   [ring.util.response :only [redirect]]
   [ring.util.servlet :only [defservice]]
   [tl.pages.programming :only [map-page]])
  (:require [compojure.route :as route]))

(defroutes tl-routes
  (GET "/" [] (redirect "/maps/")))

(defroutes map-routes
  (GET "/maps/" request (map-page request))
  (GET "/maps/:kind" request (map-page request))
  (GET "/maps/fullscreen/:kind" request (map-page request)))

(defroutes static-routes
  (route/files "/" {:root "./war/public"})
  (route/files "/" {:root "./public"}))

(defroutes error-routes
  (route/not-found "Not Found"))

(defroutes all-routes
  tl-routes
  map-routes
  static-routes
  error-routes)

(defservice all-routes)
