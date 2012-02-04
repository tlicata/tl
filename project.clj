(require '[robert.hooke :as hooke])
(require '[lancet :as lancet])
(require '[leiningen.appengine-prepare :as appengine-prepare])

(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[compojure "1.0.0"]
                 [hiccup "0.3.8"]
                 [org.clojure/clojure "1.3.0"]
                 [org.clojure/data.json "0.1.1"]
                 [ring "1.0.1"]
                 [ring/ring-jetty-adapter "0.3.9"]
                 [scriptjure "0.1.24"]]
  :dev-dependencies [[ring-mock "0.1.1"]])

(defn compile-clojurescript
  [task project & args]
  (task project)
  (println "Compiling clojurescript")
  (lancet/exec {:executable "./bin/compile-clojurescript.sh"}))

(hooke/add-hook #'appengine-prepare/appengine-prepare compile-clojurescript)
