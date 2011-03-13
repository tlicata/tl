(ns tl.pages.home-test
  (:use [tl.pages.home])
  (:use [clojure.test])
  (:use [clojure.zip :as zip]))

(defn has-script-tag? [response]
  (let [zipper (zip/vector-zip (:body response))]
	(loop [loc zipper]
	  (if (zip/end? loc)
		false
		(if (and
			 (not (branch? loc))
			 (= :script (zip/node loc)))
		  true
		  (recur (zip/next loc)))))))

(deftest test-youtubes
  (is (not (has-script-tag? (youtubes))))
  (is (has-script-tag? (youtubes "id"))))