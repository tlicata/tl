(ns com.timothylicata.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use appengine.users)
  (:use compojure.http)
  (:use com.timothylicata.pages))

(defroutes tl
  (GET "/"
    (home-page request))
  (GET "/about.html"
    (about request))
  (GET "/contact.html"
    (contact request))
  (GET "/work.html"
    (work request))
  (GET "/*"
    (or (serve-file (:* params)) :next))
  (ANY "*"
    (page-not-found)))

(defservice (wrap-with-user-info tl))
