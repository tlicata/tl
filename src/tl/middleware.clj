(ns tl.middleware
  (:use [tl.pages.global :only [*request*]]))

(defn wrap-current-link [handler]
  (fn [request]
    (binding [*request* request]
      (handler request))))
