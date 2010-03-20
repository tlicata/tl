(ns com.timothylicata.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use appengine.users)
  (:use compojure.http)
  (:use com.timothylicata.pages))

(defroutes tl
  (GET "/"
    (home-page request))
  (GET "/about.html"
    (about-page request))
  (GET "/contact.html"
    (contact request))
  (GET "/work.html"
    (work request))
  (GET "/*"
    (or (serve-file (:* params)) :next))
  (ANY "*"
    (page-not-found)))

(defroutes admin-routes
  (GET "/admin.html" (admin-page request)))

;(defroutes blog-form-routes
;  (POST "/do-add-post" (apply do-add-post
;                              (map params [:title :category :tags :markdown])))
;  (POST "/do-edit-post/:old-id" (apply do-edit-post
;                                       (map params [:id :title :category :tag :markdown]))))

(defroutes all-routes
  admin-routes
  ;blog-form-routes
  tl)

(defservice (wrap-with-user-info all-routes))
