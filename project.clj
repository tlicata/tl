(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site"
  :dependencies [[compojure "1.2.1"]
                 [hiccup "1.0.5"]
                 [markdown-clj "0.9.58"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/data.json "0.2.5"]
                 [prismatic/dommy "0.1.1"]
                 [ring "1.3.1"]]
  :hooks [leiningen.cljsbuild]
  :min-lein-version "2.1.2"
  :profiles {:dev {:dependencies [[ring-mock "0.1.1"]]
                   :plugins [[com.cemerick/austin "0.1.1"]]}
             :prod {:offline? true}
             :uberjar {:main tl.core, :aot :all}}
  :plugins [[lein-cljsbuild "1.0.3"]]
  :aot [tl.core]
  :uberjar-name "tl-standalone.jar"
  :cljsbuild {:builds {:dev {:source-paths ["cljs"]
                             :compiler {:output-to "resources/public/js/bin/all.js"
                                        :optimizations :whitespace
                                        :pretty-print true}}
                       :prod {:source-paths ["cljs"]
                              :compiler {:output-to "resources/public/js/bin/all.js"
                                         :optimizations :advanced
                                         :pretty-print false}}}})

