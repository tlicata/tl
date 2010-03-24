(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[org.clojars.making/compojure "0.4.0-SNAPSHOT"]
                 [hiccup "0.4.0-SNAPSHOT"]
                 [org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]
                 [ring "0.2.0-RC2"]
                 [com.google.appengine/appengine-tools-sdk "1.3.0"]]
  :namespaces [tl.core
               tl.pages
               tl.servlet
               tl.util]
  :compile-path "war/WEB-INF/classes/"
  :library-path "war/WEB-INF/lib/")
