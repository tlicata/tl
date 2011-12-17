(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[compojure "0.5.3"]
                 [hiccup "0.2.6"]
                 [org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [ring "1.0.0"]
                 [ring/ring-jetty-adapter "0.3.9"]
                 [scriptjure "0.1.21"]]
  :dev-dependencies [[ring-mock "0.1.1"]
                     [swank-clojure "1.2.1"]]
  :keep-non-project-classes true)


