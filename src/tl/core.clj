(ns tl.core
  (:use [noir.core :only [defpage pre-route]])
  (:require [noir.server :as server]
            [noir.session :as session]
            [tl.middleware :as mw]))

(pre-route "/admin/*" {} (when-not (session/get :admin)
                          {:status 401
                           :body "Not Authorized"}))

(server/load-views "src/tl/pages/")

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (server/start port)))

(defn dev-main []
  (server/start 8080))
