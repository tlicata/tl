(ns com.timothylicata.servlet
	(:gen-class :extends javax.servlet.http.HttpServlet)
	(:use compojure.http)
  (:use com.timothylicata.pages))

(defroutes tl
  (GET "/"
    (home-page))
  (GET "/about.html"
    (about))
  (GET "/contact.html"
    (contact))
  (GET "/work.html"
    (work))
  (GET "/*"
    (or (serve-file (:* params)) :next))
  (ANY "*"
    (page-not-found)))

(defservice tl)
