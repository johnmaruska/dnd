(ns dnd.race.dwarf
  (:require [clojure.set :refer [union]]
            [dnd.stat :as stat]
            [dnd.language :as language]
            [dnd.weapon :as weapon]))

(def traits
  {:mature-age 50
   :max-age 350
   :short-height 4
   :tall-height 5
   :alignments [alignment/lawful alignment/good]
   :base-speed 25
   :size :medium
   :features-and-traits #{:darkvision
                          :dwarven-resilience
                          :stonecunning}})

(def hill-dwarf-traits
  (-> traits
      (update :features-and-traits (partial union #{:dwarven-toughness}))))

;; Player's Handbook Ch2 - Dwarf page 20
(defn apply-race [player]
  (if (not (:race player))
    (-> player
        (assoc :race :dwarf)
        (update-in [:ability-scores stat/CON] #(stat/+ 2 %))
        (assoc-in :size :medium)
        (assoc-in :base-speed 25)
        ;; weapon proficiencies
        (update :proficiencies (partial concat [weapon/battleaxe
                                                weapon/handaxe
                                                weapon/throwing-hammer
                                                weapon/warhammer]))
        ;; TODO: user picks one of Smith's Tools, Brewer's supplies, Mason's Tools
        (update :languages (partial union #{language/common
                                            language/dwarvish}))
        )
    player))

(defn apply-hill-dwarf [player]
  (let [race (:race player)
        applicable? (and (or (nil? race) (= :dwarf race))
                         (not (:subrace player)))]
    (if applicable?
      (-> player
          apply-race
          (update-in [:ability-scores stat/WIS] stat/inc)
          (update :features-and-traits (partial union #{:dwarven-toughness})))
      player)))

(defn apply-mountain-dwarf [player]
  (let [race (:race player)
        applicable? (and (or (nil? race) (= :dwarf race))
                         (not (:subrace player)))]
    (if applicable?
      (-> player
          apply-race
          (update-in [:ability-scores stat/STR] (partial stat/+ 2))
          (update :proficiencies (partial concat [armor/light armor/medium])))
      player)))
