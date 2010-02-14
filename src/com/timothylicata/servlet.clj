(ns com.timothylicata.servlet
	(:gen-class :extends javax.servlet.http.HttpServlet)
	(:use compojure.http compojure.html)
  (:import (com.google.appengine.api.users UserServiceFactory)))

(defn print-home-page
  []
  (let [user-service (UserServiceFactory/getUserService)
        user (.getCurrentUser user-service)]
    (html
      [:h1 "Hello " (if user (.getNickname user) "Sir")]
      [:p (link-to (.createLoginURL user-service "/") "sign in")]
      [:p (link-to (.createLogoutURL user-service "/") "sign out")])))

(defroutes tl
	(GET "/favicon.ico"
		(serve-file "favicon.ico"))
	(GET "/"
    (print-home-page))
	(ANY "*"
		(page-not-found)))

(defservice tl)
