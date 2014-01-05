(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site"
  :dependencies [[clj-redis "0.0.12"]
                 [compojure "1.1.3"]
                 [lib-noir "0.2.0"]
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1933"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 [org.clojure/data.json "0.2.3"]
                 [prismatic/dommy "0.1.1"]]
  :hooks [leiningen.cljsbuild]
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[ring-mock "0.1.1"]]
                   :plugins [[com.cemerick/austin "0.1.1"]]}
             :production {:offline true}}
  :plugins [[lein-cljsbuild "0.3.4"]]
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

