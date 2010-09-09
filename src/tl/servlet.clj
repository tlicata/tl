(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use
   [compojure.core :only [defroutes GET POST]]
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
  (route/files "/" {:root "./war/public"})
  (route/files "/" {:root "./public"})
  (route/not-found "Not Found"))

(defservice tl)
