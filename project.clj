(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site"
  :dependencies [[clj-redis "0.0.12"]
                 [domina "1.0.0"]
                 [noir "1.3.0-beta10"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.1.1"]]
  :hooks [leiningen.cljsbuild]
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[ring-mock "0.1.1"]]}}
  :plugins [[lein-cljsbuild "0.2.7"]]
  :aot [tl.core]
  :cljsbuild {:builds [{:source-path "cljs"
                        :compiler {:output-to "resources/public/js/bin/all.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})

