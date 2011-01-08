(ns tl.middleware-test
  (:use [tl.middleware] :reload-all)
  (:use [clojure.test])
  (:use [ring.mock.request]))

(def uri "/contact/")
(def html [[:html [:body [:div.id [:li [:a {:href uri} "text"]]]]]])
(def done [[:html [:body [:div.id [:li [:span.current "text"]]]]]])

(deftest test-wrap-current-link
  (let [handler (fn [request] {:body html})
		wrapped (wrap-current-link handler)
		response (wrapped (request :get uri))]
	(is (= (:body response) done))))
