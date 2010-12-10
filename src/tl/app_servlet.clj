(ns tl.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use tl.core)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]]))


(defn -service [this request response]
  ((make-servlet-service-method tl) this request response))
