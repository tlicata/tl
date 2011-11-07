(require '[robert.hooke :as hooke])
(require '[lancet :as lancet])
(require '[leiningen.appengine-prepare :as appengine-prepare])

(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[compojure "0.5.3"]
                 [hiccup "0.2.6"]
                 [org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [ring "0.3.0-RC2"]
                 [scriptjure "0.1.21"]]
  :dev-dependencies [[appengine-magic "0.4.0-SNAPSHOT"]
                     [ring-mock "0.1.1"]
                     [swank-clojure "1.2.1"]]
  :aot [tl.app_servlet]
  :keep-non-project-classes true)

(defn compile-clojurescript
  [task project & args]
  (task project)
  (println "Compiling clojurescript")
  (lancet/exec {:executable "./bin/compile-clojurescript.sh"}))

(hooke/add-hook #'appengine-prepare/appengine-prepare compile-clojurescript)
