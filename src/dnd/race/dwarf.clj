(ns dnd.race.dwarf
  (:require [clojure.set :refer [union]]
            [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.stat :as stat]
            [dnd.trait :as trait]))

;; Player's Handbook Ch2 - Dwarf page 20
(def ^:private traits
  {:age {:maturity 50 :lifespan 350}
   :alignments [alignment/lawful alignment/good]
   :applicable-traits [trait/dwarven-combat-training
                       (trait/ability-score-increase stat/CON 2)]
   :base-speed 25  ;; TODO: not reduced by heavy armor
   :choosable-traits [{:tool-proficiency #{:smiths-tools
                                           :brewers-supplies
                                           :masons-tools}}]
   :features-traits #{:darkvision :dwarven-resilience :stonecunning}
   :height {:short 4 :tall 5}
   :languages #{language/common language/dwarvish}
   :size :medium
   :weight {:average 150}
   :race :dwarf})

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
