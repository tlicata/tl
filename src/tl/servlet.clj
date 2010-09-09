(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use
   [compojure.core :only [defroutes GET POST]]
   [ring.middleware.file :only [wrap-file]]
	[ring.util.servlet :only [defservice]]
	tl.pages.golf
	tl.pages.programming
	tl.pages.youtubes)
  (:require [compojure.route :as route]))

(defroutes tl
  (GET "/" [] (programming {}))
  (GET "/golf.html" [] (golf))
  (POST "/golf.html" [name] (golf-post name))
  (GET "/programming.html" [] (programming {}))
  (GET "/youtubes.html" [] (youtubes {}))
  (route/not-found "Not Found"))

(defservice
  (-> tl
	  (wrap-file "public")))