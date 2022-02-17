(ns my-reagent-app.lib.cards
  (:require [reagent.core :as r]))

(def cards (r/atom (shuffle 
                     (map-indexed (fn [id v]
                                    {
                                     :id id
                                     :value v
                                     :found false
                                     :visible false})
                          (flatten (take 2 (repeat (range 5))))))))

(def timeout (r/atom nil))

(defn show [id]
  (fn [item]
    (if (== (:id item) id)
      (conj item {:visible true})
      item)))

(defn click-on [id]
    (if (== 2 (count (filter :visible @cards))) (do
                     (js/clearTimeout @timeout)
                     (swap! cards new-turn)))
                     
    (let [card (first (filter #(== (:id %) id) @cards))
          in-turn (some :visible @cards)]
      (cond
        in-turn (check-pairs id)
        (:found card) false
        (:visible card) false
        :else (swap! cards #(map (show id) %)))))

(defn check-pairs [id]
  (let [other (first (filter :visible @cards))
        this  (first (filter #(== (:id %) id) @cards))]
    (if (== (:value  other) (:value this))
      (swap! cards (reveal other this))
      (do
        (swap! cards #(map (show id) %))
        (reset! timeout (js/setTimeout #(swap! cards new-turn) 2000))))))

(defn reveal [card1 card2]
  (fn [cards]
    (map #(if (or
                (== (:id %) (:id card1))
                (== (:id %) (:id card2)))
            (conj % {:found true :visible false})
            %) cards)))

(defn new-turn [cards]
  (map #(conj % {:visible false}) cards))

