(ns dnd.armor
  (:require [dnd.armor.category :as category]
            [dnd.player :as player]))

;; Player's Handbook p144-146

;; TODO: special rule, shield not exclusive with other armor
;; TODO: D&D classifies category Shield with item Shield alone in that category.
;;       move `def shield` to `dnd.armor.shield/shield`?
(def shield
  {:name :shield
   :category category/shield
   :cost {:gold 10}
   :armor-class-bonus 2
   :weight 6})

(defn stealth-disadvantage? [armor]
  (= :disadvantage (:stealth armor)))

;; Player's Handbook Ch1.5 page 14
(defn base-armor-class [player]
  (+ 10 (player/dex-modifier player)))

(defn ac-from-shield [player]
  (if (-> player :equipment :shield :equipped?)
    (-> player :equipment :shield :armor-class-bonus)
    0))

(defn armor-class [player]
  (let [armor (-> player :equipment :armor)
        ac-from-armor (if (and armor (:equipped armor))
                        (:armor-class armor)
                        base-armor-class)]
    (+ (ac-from-armor player)
       (ac-from-shield player))))
