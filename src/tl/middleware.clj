(ns tl.middleware
  (:require [tl.pages.global :as global])
  (:use [appengine-magic.services.user :only [user-admin? user-logged-in?]]))

(defn wrap-admin
  "If the user is logged in, display the page.  Otherwise skip this route
  (probably ends up giving a 404 error)."
  [handler]
  (fn [request]
	(if (and (user-logged-in?) (user-admin?))
	  (handler request))))

(defn wrap-layout
  "Adds a header and footer to the response in addition to supplying js,
  css, and content html."
  [handler]
  (fn [request]
	(when-let [response (handler request)]
	  (let [body (global/wrap-in-layout (:title response)
										(:css response)
										(:js response)
										(:body response))]
		(assoc response :body body)))))

