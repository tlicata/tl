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
  (GET "/" [] (home-page))
  (GET "/contact/" []  (contact-page))
  (GET "/login/" [] (login-page)))

(defroutes map-routes
  (GET "/maps/" [] (map-page))
  (GET "/maps/:kind" [kind] (map-page kind)))

(defroutes admin-routes
  (GET "/admin/" [] (admin-page)))

(defroutes error-routes
  (route/not-found "Not Found"))

(defroutes layout-routes
  tl-routes
  admin-routes)

(wrap! admin-routes mw/wrap-admin)

(defroutes all-routes
  tl-routes
  map-routes
  admin-routes
  error-routes)

(wrap! all-routes mw/wrap-layout)

(ae/def-appengine-app tl #'all-routes)
