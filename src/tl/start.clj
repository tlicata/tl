(ns tl.start
  (:use
   [ring.adapter.jetty :only [run-jetty]]
   [tl.servlet :only [all-routes]]))

(run-jetty (var all-routes) {:port 8080})