(ns tl.util
  (:import [java.text DateFormat])
  (:use [hiccup.form-helpers :only [label submit-button]]))

(defn die [& xs]
  (throw (Exception. (apply str xs))))

(defn pretty-date [date]
  (let [df (DateFormat/getDateInstance DateFormat/SHORT)]
    (. df format date)))

(defn form-row
  ([f name lab] (form-row f name lab nil))
  ([f name lab val]
   [:div
    (label name (str lab ":"))
    (f name val)]))

(defn submit-row [lab]
  [:div.submit
   (submit-button lab)])
