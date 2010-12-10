(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[compojure "0.4.1"]
                 [hiccup "0.2.6"]
                 [org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [ring "0.3.0-RC2"]]
  :dev-dependencies [[appengine-magic "0.4.0-SNAPSHOT"]
					 [swank-clojure "1.2.1"]]
  :aot [tl.app_servlet])


