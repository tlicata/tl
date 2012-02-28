(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[clj-redis "0.0.12"]
                 [compojure "1.0.0"]
                 [hiccup "0.3.8"]
                 [noir "1.2.2"]
                 [org.clojure/clojure "1.3.0"]
                 [org.clojure/data.json "0.1.1"]
                 [ring "1.0.1"]
                 [ring/ring-jetty-adapter "0.3.9"]
                 [scriptjure "0.1.24"]]
  :dev-dependencies [[lein-cljsbuild "0.1.1"]
                     [ring-mock "0.1.1"]]
  :cljsbuild
  {:builds [{:source-path "src-cljs",
             :compiler {:output-to "resources/public/js/bin/all.min.js",
                        :optimizations :advanced,
                        :pretty-print false}}]}
  :hooks [leiningen.cljsbuild]
  :warn-on-reflection false)
