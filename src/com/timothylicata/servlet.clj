(ns com.timothylicata.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use appengine.users)
  (:use compojure.http)
  (:use com.timothylicata.blog)
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

(defroutes blog-routes
  (GET "/post/:id"      (post-page (:id params)))
  (GET "/tag/:tag"      (tag-page (:tag params)))
  (GET "/add-post"      (add-post-page))
  (GET "/edit-post/:id" (edit-post-page (:id params))))

(defroutes all-routes
  blog-routes
  tl)

(defservice (wrap-with-user-info all-routes))
