(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use
   [compojure.core :only [defroutes GET]]
   [ring.util.response :only [redirect]]
   [ring.util.servlet :only [defservice]]
   [tl.pages.programming :only [map-page]])
  (:require [compojure.route :as route]))

(defroutes tl
  (GET "/" [] (redirect "/maps/"))
  (GET "/maps/" request (map-page request))
  (GET "/maps/:kind" request (map-page request))
  (GET "/maps/fullscreen/:kind" request (map-page request))
  (route/files "/" {:root "./war/public"})
  (route/files "/" {:root "./public"})
  (route/not-found "Not Found"))

(defservice tl)
