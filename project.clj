(defproject tl "0.1.0-SNAPSHOT"
  :description "My personal site"
  :dependencies [[clj-redis "0.0.12"]
                 [noir "1.2.2"]
                 [org.clojure/clojure "1.3.0"]
                 [org.clojure/data.json "0.1.1"]
                 [scriptjure "0.1.24"]]
  :dev-dependencies [[ring-mock "0.1.1"]]
  :plugins [[lein-cljsbuild "0.1.2"]]
  :aot [tl.core]
  :cljsbuild
  {:builds [{:source-path "src-cljs",
             :compiler {:output-to "resources/public/js/bin/all.min.js",
                        :optimizations :advanced,
                        :pretty-print false}}]}
  :hooks [leiningen.cljsbuild])
