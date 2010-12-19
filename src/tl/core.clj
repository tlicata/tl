(ns tl.core
  (:use
   [compojure.core :only [defroutes GET wrap!]]
   [tl.pages
	[home :only [admin-page home-page contact-page login-page]]
	[maps :only [map-page]]])
  (:require [appengine-magic.core :as ae]
			[compojure.route :as route]
			[tl.middleware :as mw]))

(defroutes tl-routes
  (GET "/" request (home-page request))
  (GET "/contact/" request (contact-page request))
  (GET "/login/" request (login-page request)))

(defroutes map-routes
  (GET "/maps/" request (map-page request))
  (GET "/maps/:kind" request (map-page request)))

(defroutes admin-routes
  (GET "/admin/" request (admin-page request)))

(defroutes error-routes
  (route/not-found "Not Found"))

(wrap! admin-routes mw/wrap-admin)

(defroutes all-routes
  tl-routes
  map-routes
  admin-routes
  error-routes)

(ae/def-appengine-app tl #'all-routes)
