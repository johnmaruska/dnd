(ns dnd.race.dwarf
  (:require [clojure.set :refer [union]]
            [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.trait :as trait]))

;; Player's Handbook Ch2 - Dwarf page 20
(def ^:private traits
  {:race :dwarf
   :age {:maturity 50
         :lifespan 350}
   :alignments [alignment/lawful alignment/good]
   :base-speed 25  ;; TODO: not reduced by heavy armor
   :size {:class :medium
          :low-end 4
          :high-end 5
          :estimated-weight 150}
   :languages #{language/common language/dwarvish}
   :features-traits #{:darkvision
                      :dwarven-resilience
                      :stonecunning}
   :applicable-traits [trait/dwarven-combat-training
                       (trait/ability-score-increase stat/CON 2)]
   :choosable-traits [{:tool-proficiency #{:smiths-tools :brewers-supplies :masons-tools}}]})

(def hill-dwarf-traits
  (-> traits
      (assoc :subrace :hill-dwarf)
      (update :features-traits (partial union #{:dwarven-toughness}))
      (update :applicable-traits
              (partial cons (trait/ability-score-increase stat/WIS 1)))))

(def mountain-dwarf-traits
  (-> traits
      (assoc :subrace :mountain-dwarf)
      (update :applicable-traits
              (partial concat [trait/dwarven-armor-training
                               (trait/ability-score-increase stat/STR 2)]))))
