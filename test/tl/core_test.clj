(ns tl.core-test
  (:use [tl.core] :reload-all)
  (:use [clojure.test])
  (:use [noir.util.test])
  (:use [ring.mock.request]))

(def valid-routes ["/"
                   "/contact/"
                   "/login/"
                   "/ltcc/"
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

(def admin-routes ["/admin/"
                   "/admin/ltcc/"])

(defn is-status [route status]
  (-> (send-request route)
      (has-status status)))

(deftest test-routes
  (doseq [route valid-routes]
    (is-status route 200)))

(deftest test-invalid-routes
  (doseq [route invalid-routes]
    (is-status route 404)))

(deftest test-auth
  (doseq [route admin-routes]
    (is-status route 401)))
