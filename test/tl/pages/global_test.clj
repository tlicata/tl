(ns tl.pages.global-test
  (:use [tl.pages.global] :reload-all)
  (:use [clojure.test]))

(deftest test-reduce-blurbs
  (let [a {:html [[:p "a"]]
           :css ["a.css"]
           :js ["a.js"]
           :title ["A title"]}
        b {:html [[:p "b"]]
           :css ["b.css"]
           :js ["b.js"]
           :title ["B title"]}]
    (is (= {:html [[:p "a"] [:p "b"]]
            :css ["a.css" "b.css"]
            :js ["a.js" "b.js"]
            :title ["A title" "B title"]}
           (reduce-blurbs a b)))))
