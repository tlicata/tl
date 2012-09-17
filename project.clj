(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site"
  :dependencies [[clj-redis "0.0.12"]
                 [noir "1.3.0-beta10"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.1.1"]]
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[ring-mock "0.1.1"]]}}
  :aot [tl.core])
