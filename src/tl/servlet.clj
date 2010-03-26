(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use [compojure.core :only [defroutes GET ANY]])
  (:use [ring.util.servlet :only [defservice]])
  (:use [ring.util.response :only [file-response]])
  (:use tl.pages))

(defroutes tl
  (GET "/" [] (home-page {}))
  (GET "/about.html" [] (about-page {}))
  (GET "/admin.html" [] (admin-page {}))
  (GET "/contact.html" [] (contact {}))
  (GET "/work.html" [] (work {}))
  (GET "/*" {params :params} (file-response (str "public/" (:* params))))
  (ANY "*" [] {:status 404 :body "<h1>404</h1>"}))

(defservice tl)
