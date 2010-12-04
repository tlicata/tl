(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use
   [compojure.core :only [defroutes GET wrap!]]
   [ring.middleware.file-info :only [wrap-file-info]]
   [ring.util.response :only [redirect]]
   [ring.util.servlet :only [defservice]]
   [tl.pages
	[home :only [home-page contact-page]]
	[maps :only [map-page]]])
  (:require [appengine-magic.core :as ae]
			[compojure.route :as route]))

(defroutes tl-routes
  (GET "/" request (home-page request))
  (GET "/contact/" request (contact-page request)))

(defroutes map-routes
  (GET "/maps/" request (map-page request))
  (GET "/maps/:kind" request (map-page request)))

(defroutes error-routes
  (route/not-found "Not Found"))

(defroutes all-routes
  tl-routes
  map-routes
  error-routes)

(defservice all-routes)
(ae/def-appengine-app tl #'all-routes)
