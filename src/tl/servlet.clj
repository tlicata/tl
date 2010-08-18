(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use [compojure.core :only [defroutes GET POST]]
	[ring.util.servlet :only [defservice]]
	tl.pages.golf
	tl.pages.home
	tl.pages.programming
	tl.pages.youtubes)
  (:require [compojure.route :as route]))

(defroutes tl
  (GET "/" [] (home-page {}))
  (GET "/golf.html" [] (golf))
  (POST "/golf.html" [name] (golf-post name))
  (GET "/programming.html" [] (programming {}))
  (GET "/youtubes.html" [] (youtubes {}))
  (route/files "/" {:root "war"})
  (route/not-found "Not Found"))

(defservice tl)












