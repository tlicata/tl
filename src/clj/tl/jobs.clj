(ns tl.jobs
  (:require [tl.pages.youtubes :refer [youtubes-update-titles]]))

(def worker (atom nil))

(defn start-worker []
  (reset! worker (future
                   (while true
                     (Thread/sleep 300000)
                     (println "running jobs")
                     (youtubes-update-titles)))))

(defn stop-worker []
  (when (future? @worker)
    (future-cancel @worker)
    (reset! worker nil)))

(defn jobs-page []
  (if (future? @worker)
    (do
      (stop-worker)
      {:body "Stopped"})
    (do
      (start-worker)
      {:body "Started"})))
