(ns com.timothylicata.servlet
	(:gen-class :extends javax.servlet.http.HttpServlet)
	(:use compojure.http)
  (:use com.timothylicata.pages))

(defroutes tl
  (GET "/"
    (print-home-page))
  (GET "/*"
    (or (serve-file (:* params)) :next))
  (ANY "*"
    (page-not-found)))

(defservice tl)
