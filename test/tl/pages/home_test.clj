(ns tl.pages.home-test
  (:use [tl.pages.home])
  (:use [clojure.test])
  (:require [clojure.zip :as zip]))

(defn has-script-tag? [response]
  (let [zipper (zip/vector-zip (:body response))]
    (loop [loc zipper]
      (if (zip/end? loc)
        false
        (if (and
             (not (zip/branch? loc))
             (= :script (zip/node loc)))
          true
          (recur (zip/next loc)))))))

(deftest test-youtubes
  (is (not (has-script-tag? (youtubes nil nil))))
  (is (not (has-script-tag? (youtubes "id" "search term")))))
