(ns tl.tictactoe-shared
  (:require
   [dommy.core :as dommy])
  (:use-macros
   [dommy.macros :only [sel]]))

(def EMPTY "")
(def X "X")
(def O "O")

(defn create [] (repeat 9 EMPTY))

(defn rows [data] (partition 3 data))

(defn cols [data]
  (map #(take-nth 3 (drop % data)) (range 0 3)))

(defn diags [data]
  [[(first data) (nth data 4) (nth data 8)]
   [(nth data 2) (nth data 4) (nth data 6)]])

(defn all-lines [data]
  (concat (rows data) (cols data) (diags data)))

(defn all-same? [line]
  (let [who (first line)]
    (and (not= who EMPTY)
         (every? #(= who %) line))))

(defn win? [data]
  (some all-same? (all-lines data)))

(defn full? [data]
  (not (some #(= EMPTY %) data)))

(defn get-squares [table-dom]
  (sel table-dom :.square))

(defn view-to-data [view]
  (map dommy/text (get-squares view)))
