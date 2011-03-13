(ns tl.core-test
  (:use [tl.core] :reload-all)
  (:use [clojure.test])
  (:use [ring.mock.request])
  (:require [appengine-magic.testing :as ae-testing]))

(def valid-routes ["/"
				   "/contact/"
				   "/login/"
				   "/maps/"
				   "/maps/google"
				   "/maps/polymaps"
				   "/youtubes/"
				   "/youtubes/id"])

(def invalid-routes ["/garbage"
					 "/garbage/"
					 "/maps/garbage"
					 "/maps/garbage/"])

(use-fixtures :each (ae-testing/local-services :all))

(deftest test-routes
  (doseq [route valid-routes]
	(let [response (all-routes (request :get route))]
	  (is (= (:status response) 200) route))))

(deftest test-invalid-routes
  (doseq [route invalid-routes]
	(let [response (all-routes (request :get route))]
	  (is (= (:status response) 404) route))))
		 
