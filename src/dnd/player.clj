(ns dnd.player
  (:require [dnd.race :as race]
            [dnd.stat :as stat]
            [dnd.util :as util]))

(def example-player
  {:level 1
   :experience 0
   :proficiency-bonus +2
   :ability-scores {stat/CHA 1
                    stat/CON 2
                    stat/DEX 3
                    stat/INT 4
                    stat/STR 5
                    stat/WIS 6}
   ;; TODO: split proficiencies into categories?
   :proficiencies []
   :race race/dwarf
   :base-speed 25
   :size :medium
   :features-and-traits #{:darkvision}
   })

(def blank-slate
  {:level 1
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
  [player stat]
  ;; magic numbers derived from tables provided in PHB
  (-> (util/div (- (get-in player [:ability-scores stat])
                   10)
                2)))

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
