(ns tl.middleware
  (:require [clojure.zip :as zip]
			[tl.pages.global :as global])
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
	  (if (vector? (:body response))
		(let [body (global/wrap-in-layout (:title response)
										  (:css response)
										  (:js response)
										  (:body response))]
		  (assoc response :body body))
		response))))

(defn wrap-html
  [handler]
  (fn [request]
	(when-let [response (handler request)]
	  (let [body (global/convert-to-html (:body response))]
		(assoc response :body body)))))

(defn- current-link
  [html uri]
  (let [zip (zip/vector-zip html)
		link? (fn [loc]
				(and (zip/branch? loc)
					 (= (zip/node (zip/down loc)) :a)
					 (= (zip/node (zip/right (zip/down loc))) {:href uri})))
		text (fn [loc]
			   [:span.current (-> loc zip/down zip/right zip/right zip/node)])]
	(loop [loc zip]
	  (if (zip/end? loc)
		(zip/root loc)
		(recur (zip/next (if (link? loc)
						   (zip/replace loc (text loc))
						   loc)))))))

(defn wrap-current-link
  "Delinkify links that point to the current page. Adds a css
  class name onto it can be styled differently."
  [handler]
  (fn [request]
	(when-let [response (handler request)]
	  (let [new-body (current-link (:body response) (:uri request))]
		(assoc response :body new-body)))))
