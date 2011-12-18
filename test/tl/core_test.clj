(ns tl.core-test
  (:use [tl.core] :reload-all)
  (:use [clojure.test])
  (:use [ring.mock.request]))

(def valid-routes ["/"
                   "/contact/"
                   "/login/"
                   "/maps/"
                   "/maps/google"
                   "/photos/"
                   "/photos/end-of-era"
                   "/youtubes/"
                   "/youtubes/id"])

(def invalid-routes ["/garbage"
                     "/garbage/"
                     "/maps/garbage"
                     "/maps/garbage/"
                     "/maps/polymaps"
                     "/photos/garbage"])

(deftest test-routes
  (doseq [route valid-routes]
    (let [response (all-routes (request :get route))]
      (is (= (:status response) 200) route))))

(deftest test-invalid-routes
  (doseq [route invalid-routes]
    (let [response (all-routes (request :get route))]
      (is (= (:status response) 404) route))))
         
