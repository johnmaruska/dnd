(ns dnd.player
  (:require [dnd.stat :as stat]
            [dnd.util :as util]))

(def blank-slate
  {:level 1
   ;; race
   ;; size
   ;; base-speeed
   ;; features-traits
   ;; choosable-traits
   :experience 0
   :proficiency-bonus 2
   :languages #{}})

(defn set-level [player level]
  (-> player
      (assoc :level (min 20 (max 1 level)))))

(defn level-up [player]
  (set-level player (inc (:level player))))

;; Player's Handbook Ch1.3 page 13
(defn get-ability-modifier
  "Retrieve derived trait "
  [player ability]
  ;; magic numbers derived from tables provided in PHB
  (-> (get-in player [:ability-scores ability])
      (- 10)
      (util/div 2)))

;; Player's Handbook Ch1.5 page 14
(defn armor-class [player]
  ;; TODO: more rules later
  (+ 10 (get-ability-modifier player stat/DEX)))

(defn proficiency-bonus [player]
  ;; magic numbers derived from tables provided in PHB
  (-> (:level player)
      (util/div 4)
      (+ 2)))

;; TODO: split proficiencies into categories?
(defn proficient? [player skill]
  (contains? (:proficiencies player) skill))
