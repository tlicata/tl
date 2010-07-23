(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[org.clojars.making/compojure "0.4.0-SNAPSHOT"]
                 [hiccup "0.4.0-SNAPSHOT"]
                 [org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]
                 [ring "0.2.0"]
                 [com.google.appengine/appengine-api-1.0-sdk "1.3.1"]]
  :dev-dependencies [[swank-clojure "1.2.1"]]
  :compile-path "war/WEB-INF/classes/"
  :library-path "war/WEB-INF/lib/")
