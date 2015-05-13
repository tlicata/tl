(ns tl.jobs
  (:require [tl.pages.youtubes :refer [youtubes-update-titles]]))

(def worker (atom nil))

(defn start-worker []
  (reset! worker (future
                   (while true
                     (Thread/sleep 3600000)
                     (println "running jobs")
                     (youtubes-update-titles)))))

(defn stop-worker []
  (when (future? @worker)
    (future-cancel @worker)
    (reset! worker nil)))

(start-worker)
