(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site"
  :dependencies [[compojure "1.2.1"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/data.json "0.2.5"]
                 [prismatic/dommy "0.1.1"]
                 [ring "1.3.1"]]
  :hooks [leiningen.cljsbuild]
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[ring-mock "0.1.1"]]
                   :plugins [[com.cemerick/austin "0.1.1"]]}
             :production {:offline true}}
  :plugins [[lein-cljsbuild "1.0.3"]]
  :aot [tl.core]
  :cljsbuild {:builds [{:source-paths ["cljs"]
                        :id "dev"
                        :compiler {:output-to "resources/public/js/bin/all.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}
                       {:source-paths ["cljs"]
                        :id "prod"
                        :compiler {:output-to "resources/public/js/bin/all.min.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]})

