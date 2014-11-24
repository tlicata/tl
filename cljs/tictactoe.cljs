(ns tl.tictactoe
  (:require
   [dommy.core :as dommy]
   [tl.tictactoe-shared :as ttt])
  (:use-macros
   [dommy.macros :only [node sel sel1]]))

(def player (atom ttt/X))
(def board-data (atom nil))
(def board-dom (atom nil))

(defn initialize-view [view]
  (doall (map #(dommy/set-text! % "") (ttt/get-squares view))))

(defn process-click [event]
  (let [elem (. event -target)]
    (if (= ttt/EMPTY (dommy/text elem))
      (do
        (dommy/set-text! elem @player)
        (swap! board-data #(ttt/view-to-data @board-dom))
        (if (ttt/win? @board-data)
          (do
            (js/alert (str "winner " @player))
            (tear-down)
            (set-up))
          (if (ttt/full? @board-data)
            (do
              (js/alert "everyone loses")
              (tear-down)
              (set-up))
            (swap! player #(if (= % ttt/X) ttt/O ttt/X))))))))

(defn listen [table-dom]
  (doseq [square (ttt/get-squares table-dom)]
    (dommy/listen! square :click process-click)))

(defn unlisten [table-dom]
  (doseq [square (ttt/get-squares table-dom)]
    (dommy/unlisten! square :click process-click)))

(defn set-up []
  (swap! board-data ttt/create)
  (swap! board-dom #(sel1 :#ttt))
  (doto @board-dom
    (initialize-view)
    (listen)))

(defn tear-down []
  (unlisten @board-dom)
  (swap! board-dom (constantly nil))
  (swap! board-data (constantly nil)))

;; (set-up)
