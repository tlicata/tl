(ns tl.middleware-test
  (:use [tl.middleware] :reload-all)
  (:use [clojure.test])
  (:use [ring.mock.request]))

(def rel "id")
(def uri (str "/youtubes/" rel))
(def html-rel [[:html [:body [:div.id [:li [:a {:href rel} "text"]]]]]])
(def html-uri [[:html [:body [:div.id [:li [:a {:href uri} "text"]]]]]])
(def done [[:html [:body [:div.id [:li [:span.current "text"]]]]]])

(deftest test-wrap-current-link
  (let [handler (fn [request] {:body html-uri})
        wrapped (wrap-current-link handler)
        response (wrapped (request :get uri))]
    (is (= (:body response) done))))

(deftest test-wrap-current-link-relative
  (let [handler (fn [request] {:body html-rel})
        wrapped (wrap-current-link handler)
        response (wrapped (request :get uri))]
    (is (= (:body response) done))))
