(ns tl.core
  (:use
   [compojure.core :only [defroutes GET]]
   [tl.pages
	[home :only [admin-page home-page contact-page login-page youtubes]]
	[maps :only [map-page]]])
  (:require [appengine-magic.core :as ae]
			[compojure.route :as route]
			[tl.middleware :as mw]))

(defroutes tl-routes
  (GET "/" [] (home-page))
  (GET "/contact/" []  (contact-page))
  (GET "/login/" [] (login-page))
  (GET "/youtubes/" [] (youtubes))
  (GET "/youtubes/:video" [video] (youtubes video)))

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

(def all
	 (-> #'all-routes
		 mw/wrap-layout
		 mw/wrap-current-link
		 mw/wrap-html))

(ae/def-appengine-app tl #'all)
