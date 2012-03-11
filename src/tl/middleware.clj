(ns tl.middleware
  (:require [clojure.zip :as zip]
            [tl.pages.global :as global]
            [tl.user :as user]))

(defn- current-link
  [html uri]
  (let [zip (zip/vector-zip html)
        link? (fn [loc]
                (and (zip/branch? loc)
                     (= (zip/node (zip/down loc)) :a)
                     (let [link (zip/node (zip/right (zip/down loc)))
                           rel (merge-with str {:href (re-find #".*\/" uri)} link)]
                       (or
                        (= link {:href uri})
                        (= rel {:href uri})))))
        text (fn [loc]
               [:span.current (-> loc zip/down zip/right zip/right zip/node)])]
    (loop [loc zip]
      (if (zip/end? loc)
        (zip/root loc)
        (recur (zip/next (if (link? loc)
                           (zip/replace loc (text loc))
                           loc)))))))

(defn wrap-current-link
  "Delinkify links that point to the current page. Adds a css
  class name onto it can be styled differently."
  [handler]
  (fn [request]
    (when-let [response (handler request)]
      (let [new-body (current-link (:body response) (:uri request))]
        (assoc response :body new-body)))))
