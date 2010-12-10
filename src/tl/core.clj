(ns tl.core
  (:require [appengine-magic.core :as ae])
  (:use
   [compojure.core :only [defroutes GET wrap!]]
   [ring.middleware.file-info :only [wrap-file-info]]
   [ring.util.response :only [redirect]]
   [ring.util.servlet :only [defservice]]
   [tl.pages
	[home :only [admin-page home-page contact-page]]
	[maps :only [map-page]]])
  (:require [appengine-magic.core :as ae]
			[compojure.route :as route]
			[tl.middleware :as mw]))

(defroutes tl-routes
  (GET "/" request (home-page request))
  (GET "/contact/" request (contact-page request)))

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
