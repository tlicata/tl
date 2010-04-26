(ns tl.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use [compojure.core :only [defroutes ANY GET POST]])
  (:use [ring.util.servlet :only [defservice]])
  (:use [ring.util.response :only [file-response]])
  (:use [tl.db :only [do-add-post do-edit-post]])
  (:use tl.pages))

(defroutes tl
  (GET "/" [] (home-page {}))
  (GET "/golf.html" [] (golf {}))
  (GET "/programming.html" [] (programming {}))
  (GET "/youtubes.html" [] (youtubes {}))
  (ANY "*" [] {:status 404 :body "<h1>404</h1>"}))

(defroutes admin
  (GET "/admin/" [] (admin-page {}))
  (POST "/admin/add-post" request (do-add-post (:params request)))
  (POST "/admin/edit-post" request (do-edit-post (:params request))))

(defroutes all
  admin
  tl)

(defservice all)
