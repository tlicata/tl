(ns tl.middleware
  (:use [appengine-magic.services.user :only [user-admin? user-logged-in?]]))

(defn wrap-admin
  "If the user is logged in, display the page.  Otherwise skip this route
  (probably ends up giving a 404 error)."
  [handler]
  (fn [request]
	(if (and (user-logged-in?) (user-admin?))
	  (handler request))))
