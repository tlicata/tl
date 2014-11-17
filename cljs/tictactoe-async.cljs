(ns tl.tictactoe-async
  (:require
   [cljs.core.async :as async :refer [<! >! chan put!]]
   [dommy.core :as dommy])
  (:require-macros
   [cljs.core.async.macros :as m :refer [go]])
  (:use-macros
   [dommy.macros :only [node sel sel1]]))

(def EMPTY "")
(def X "X")
(def O "O")

(def click-channel (chan))

(defn create [] (repeat 9 EMPTY))

(defn rows [data] (partition 3 data))

(defn cols [data]
  (let [first-col (take-nth 3 data)
        second-col (take-nth 3 (drop 1 data))
        third-col (take-nth 3 (drop 2 data))]
    [first-col second-col third-col]))

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

(defn render [dom data]
  (let [squares (get-squares dom)
        pairs (map vector squares data)]
    (doseq [pair pairs]
      (dommy/set-text! (first pair) (second pair)))))

(defn create-dom []
  (sel1 :#ttt))

(defn view-to-data [view]
  (map dommy/text (get-squares view)))

(defn send-to-click-channel [event]
  (let [elem (. event -target)]
    (when (= EMPTY (dommy/text elem))
      (put! click-channel event))))

(defn listen [table-dom]
  (doseq [square (get-squares table-dom)]
    (dommy/listen! square :click send-to-click-channel)))

(defn game-loop [first-player]
  (let [empty-board (create)
        dom (create-dom)]
    (listen dom)
    (go
     (loop [board empty-board player first-player]
       (render dom board)
       (let [event (<! click-channel)
             elem (. event -target)]
         (dommy/set-text! elem player)
         (let [new-board (view-to-data dom)
               next-player (if (= player X) O X)]
           (if (win? new-board)
             (do
               (js/alert (str player " won"))
               (recur (create) first-player))
             (if (full? new-board)
               (do
                 (js/alert "everyone loses")
                 (recur (create) first-player))
               (recur new-board next-player)))))))))

(game-loop X)
