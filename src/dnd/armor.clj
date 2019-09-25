(ns dnd.armor
  (:require
   [clojure.set :refer [union]]
   [dnd.armor.category :as category]
   [dnd.equipment :refer [equipped-item]]
   [dnd.player :as player]))

;; Player's Handbook p144-146

;; TODO: special rule, shield not exclusive with other armor
;; TODO: D&D classifies category Shield with item Shield alone in that category.
;;       move `def shield` to `dnd.armor.shield/shield`?
(def shield
  {:name              :shield
   :category          category/shields
   :cost              {:gold 10}
   :armor-class-bonus 2
   :weight            6})

(defn stealth-disadvantage? [armor]
  (= :disadvantage (:stealth armor)))

;; Player's Handbook Ch1.5 page 14
(defn base-armor-class [player]
  (+ 10 (player/dex-modifier player)))

(defn ac-from-shield [player]
  (or (when-let [offhand (equipped-item player :off-hand)]
        (when (category/shield? offhand)
          (:armor-class-bonus offhand)))
      0))

(defn armor-class [player]
  (let [armor (-> player :equipment :armor)
        armor-ac (when-let [armor (equipped-item player :armor)]
                   (armor :armor-class))]
    (+ ((or armor-ac
            base-armor-class) player)
       (or (ac-from-shield player) 0))))

(def proficient? #(player/proficient? %1 :armor %2))
