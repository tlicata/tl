(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site written in Clojure"
  :dependencies [[compojure "1.0.0"]
                 [hiccup "0.3.8"]
                 [org.clojure/clojure "1.3.0"]
                 [org.clojure/data.json "0.1.1"]
                 [ring "1.0.1"]
                 [ring/ring-jetty-adapter "0.3.9"]
                 [scriptjure "0.1.24"]]
  :dev-dependencies [[lein-cljsbuild "0.0.11"]
                     [ring-mock "0.1.1"]]
  :cljsbuild {:source-path "src-cljs/tl"
              :compiler {:output-to "war/js/bin/all.js"
                         :optimizations :advanced}}
  :hooks [leiningen.cljsbuild])
