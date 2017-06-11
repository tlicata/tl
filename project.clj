(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site"
  :dependencies [[clj-http "3.6.1"]
                 [com.taoensso/carmine "2.16.0"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [markdown-clj "0.9.99"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.562"]
                 [org.clojure/core.async "0.3.443" :exclusions [org.clojure/tools.reader]]
                 [org.clojure/data.json "0.2.6"]
                 [prismatic/dommy "1.1.0"]
                 [ring "1.6.0"]
                 [ring/ring-json "0.4.0"]]
  :hooks [leiningen.cljsbuild]
  :min-lein-version "2.1.2"
  :profiles {:dev {:dependencies [[ring/ring-mock "0.3.0"]]}
             :prod {:offline? true}
             :uberjar {:main tl.core, :aot :all}}
  :plugins [[lein-cljsbuild "1.1.6"]]
  :source-paths ["src/clj"]
  :aot [tl.core]
  :uberjar-name "tl-standalone.jar"
  :cljsbuild {:builds {:dev {:source-paths ["src/cljs"]
                             :compiler {:output-to "resources/public/js/bin/all.js"
                                        :optimizations :whitespace
                                        :pretty-print true}}
                       :prod {:source-paths ["src/cljs"]
                              :compiler {:output-to "resources/public/js/bin/all.js"
                                         :optimizations :advanced
                                         :pretty-print false}}}})
