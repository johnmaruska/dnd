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
   :feature-traits #{:darkvision :dwarven-resilience :stonecunning}
   :languages #{language/common language/dwarvish}
   :size :medium
   :weight {:average 150}
   :race :dwarf})

(def hill-dwarf-traits
  (-> traits
      (assoc :subrace :hill-dwarf
             :height {:base {:feet 3 :inches 8} :modifier {:d4 2}}
             :weight {:base 115 :modifier {:d6 2}})
      (update :feature-traits union #{:dwarven-toughness})
      (update :applicable-traits conj (trait/ability-score-increase stat/WIS 1))))

(def mountain-dwarf-traits
  (-> traits
      (assoc :subrace :mountain-dwarf
             :height {:base {:feet 4 :inches 0} :modifier {:d4 2}}
             :weight {:base 130 :modifier {:d6 2}})
      (update :applicable-traits concat [trait/dwarven-armor-training
                                         (trait/ability-score-increase stat/STR 2)])))
