(ns tl.user-test
  (:use [tl.user] :reload-all)
  (:use [clojure.test])
  (:use [noir.util.test])
  (:require [noir.util.crypt :as crypt]))

(deftest no-user
  (with-noir
    (is (nil? (admin?)))
    (is (nil? (current-user)))))

(deftest logged-in
  (with-noir
    (with-redefs [stored-pass (fn [_] (crypt/encrypt "bar"))]
      (login! {:username "foo" :password "bar"}))
    (is (admin?))
    (is (= (current-user) "foo"))))