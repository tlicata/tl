(ns tl.core
  (:use [noir.core :only [defpage]])
  (:require [noir.server :as server]
            [tl.middleware :as mw]))

(server/load-views "src/tl/pages/")

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (server/start port)))

(defn dev-main []
  (server/start 8080))
