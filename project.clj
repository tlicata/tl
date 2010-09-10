(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[com.google.appengine/appengine-api-1.0-sdk "1.3.1"]
                 [compojure "0.4.1"]
                 [hiccup "0.2.6"]
                 [org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [ring "0.3.0-RC2"]]
  :dev-dependencies [[swank-clojure "1.2.1"]]
  :compile-path "war/WEB-INF/classes/"
  :library-path "war/WEB-INF/lib/"
  :aot [tl.servlet])


