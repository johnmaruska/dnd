(ns dnd.race.dwarf
  (:require [clojure.set :refer [union]]
            [dnd.alignment :as alignment]
            [dnd.armor :as armor]
            [dnd.language :as language]
            [dnd.stat :as stat :refer [increase-ability-score]]
            [dnd.weapon :as weapon]))

(defn apply-dwarven-combat-training [player]
  (update player :proficiencies (partial union #{weapon/battleaxe
                                                 weapon/handaxe
                                                 weapon/throwing-hammer
                                                 weapon/warhammer})))

(defn apply-dwarven-armor-training [player]
  (update player :proficiencies (partial union #{armor/light
                                                 armor/medium})))

;; TODO: Stonecunning
;; TODO Dwarven Resilience

;; Player's Handbook Ch2 - Dwarf page 20
(def ^:private traits
  {:race :dwarf
   :mature-age 50
   :max-age 350
   :short-height 4
   :tall-height 5
   :alignments [alignment/lawful alignment/good]
   :base-speed 25
   :size :medium
   :languages #{language/common language/dwarvish}
   :features-traits #{:darkvision
                      :dwarven-resilience
                      :stonecunning}
   :applicable-traits [{:dwarven-combat-training apply-dwarven-combat-training}
                       {:ability-score-increase #(increase-ability-score % stat/CON 2)}]
   :choosable-traits [{:tool-proficiency #{:smiths-tools :brewers-supplies :masons-tools}}]})

(def hill-dwarf-traits
  (-> traits
      (assoc :subrace :hill-dwarf)
      (update :features-traits (partial union #{:dwarven-toughness}))
      (update :applicable-traits
              (partial cons {:ability-score-increase #(increase-ability-score % stat/WIS 1)}))))

(def mountain-dwarf-traits
  (-> traits
      (assoc :subrace :mountain-dwarf)
      (update applicable-traits
              (partial concat [{:dwarven-armor-training apply-dwarven-armor-training}
                               {:ability-score-increase #(increase-ability-score % stat/STR 2)}]))))
