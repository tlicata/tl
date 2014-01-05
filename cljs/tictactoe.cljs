(ns tl.tictactoe
  (:require
   [dommy.core :as dommy])
  (:use-macros
   [dommy.macros :only [node sel sel1]]))

(def EMPTY "_")
(def X "x")
(def O "o")

(def player (atom X))
(def board-data (atom nil))
(def board-dom (atom nil))

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

(defn render-row [line]
  [:tr (map (fn [sq] [:td sq]) line)])

(defn data-to-view [data]
  [:table
   [:tbody
    (map render-row (rows data))]])

(defn get-squares [table-dom]
  (sel table-dom :td))

(defn view-to-data [view]
  (map dommy/text (get-squares view)))

(defn process-click [event]
  (let [elem (. event -target)]
    (if (= EMPTY (dommy/text elem))
      (do
        (dommy/set-text! elem @player)
        (swap! board-data #(view-to-data @board-dom))
        (if (win? @board-data)
          (do
            (js/alert (str "winner " @player))
            (tear-down)
            (set-up))
          (if (full? @board-data)
            (do
              (js/alert "everyone loses")
              (tear-down)
              (set-up))
            (swap! player #(if (= % X) O X))))))))

(defn listen [table-dom]
  (doseq [square (get-squares table-dom)]
    (dommy/listen! square :click process-click)))

(defn unlisten [table-dom]
  (doseq [square (get-squares table-dom)]
    (dommy/unlisten! square :click process-click)))

(defn stylize [table-dom]
  (let [squares (get-squares table-dom)]
    (do
      (dommy/set-style! table-dom
                        :border "solid 3px black"
                        :border-collapse "collapse")
      (doseq [td squares]
        (dommy/set-style! td
                          :border "solid 3px black"
                          :cursor "pointer"
                          :font-size "2em"
                          :padding "5px")))))

(defn set-up []
  (swap! board-data create)
  (swap! board-dom #(node (data-to-view @board-data)))
  (doto @board-dom
    (listen)
    (stylize))
  (dommy/append! (sel1 :body) @board-dom))

(defn tear-down []
  (unlisten @board-dom)
  (dommy/remove! @board-dom)
  (swap! board-dom (constantly nil))
  (swap! board-data (constantly nil)))

;; (set-up)