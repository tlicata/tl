(ns tl.core
  (:use ring.adapter.jetty))

(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello world from Ring"})

(defn boot []
  (run-jetty #'handler {:port 8080}))