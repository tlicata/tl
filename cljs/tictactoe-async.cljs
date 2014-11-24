(ns tl.tictactoe-async
  (:require
   [cljs.core.async :as async :refer [<! >! chan put!]]
   [dommy.core :as dommy]
   [tl.tictactoe-shared :as ttt])
  (:require-macros
   [cljs.core.async.macros :as m :refer [go]])
  (:use-macros
   [dommy.macros :only [node sel sel1]]))

(def click-channel (chan))

(defn render [dom data]
  (let [squares (ttt/get-squares dom)
        pairs (map vector squares data)]
    (doseq [pair pairs]
      (dommy/set-text! (first pair) (second pair)))))

(defn create-dom []
  (sel1 :#ttt))

(defn view-to-data [view]
  (map dommy/text (ttt/get-squares view)))

(defn send-to-click-channel [event]
  (let [elem (. event -target)]
    (when (= ttt/EMPTY (dommy/text elem))
      (put! click-channel event))))

(defn listen [table-dom]
  (doseq [square (ttt/get-squares table-dom)]
    (dommy/listen! square :click send-to-click-channel)))

(defn game-loop [first-player]
  (let [empty-board (ttt/create)
        dom (create-dom)]
    (listen dom)
    (go
     (loop [board empty-board player first-player]
       (render dom board)
       (let [event (<! click-channel)
             elem (. event -target)]
         (dommy/set-text! elem player)
         (let [new-board (view-to-data dom)
               next-player (if (= player ttt/X) ttt/O ttt/X)]
           (if (ttt/win? new-board)
             (do
               (js/alert (str player " won"))
               (recur (ttt/create) first-player))
             (if (ttt/full? new-board)
               (do
                 (js/alert "everyone loses")
                 (recur (ttt/create) first-player))
               (recur new-board next-player)))))))))

(game-loop ttt/X)
