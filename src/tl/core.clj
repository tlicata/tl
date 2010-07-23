(ns tl.core
  (:use compojure.core
	hiccup.core
	ring.adapter.jetty))

(defroutes greeter
  (GET "/" []
       (html [:h1 "Hello World"])))

(run-jetty greeter {:port 8080})