(ns tl.start
  (:use
   [ring.adapter.jetty :only [run-jetty]]
   [tl.core :only [tl]]))

(run-jetty (var tl) {:port 8080})