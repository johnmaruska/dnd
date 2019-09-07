(ns dnd.race.halfling
  (:require
   [dnd.alignment :as alignment]
   [dnd.language :as language]
   [dnd.stat :as stat]
   [dnd.trait :as trait]))

;; Player's Handbook Ch2 - Halfling page 26
(def ^:private traits
  {:age {:maturity 20 :lifespan 250}
   :alignments [alignment/lawful alignment/good]
   :applicable-traits [(trait/ability-score-increase stat/DEX 2)]
   :base-speed 25
   :choosable-traits []
   :features-traits #{:lucky :brave :halfling-nimbleness}
   :height {:base {:feet 2 :inches 7} :modifier {:d4 2}}
   :languages #{language/common language/halfling}
   :size :small
   :weight {:base 35 :modifier nil}
   :race :halfling})

(def lightfoot-halfling-traits
  (-> traits
      (update :applicable-traits conj (trait/ability-score-increase stat/CHA 2))
      (update :feature-traits conj :naturally-stealthy)))

(def stout-halfling-traits
  (-> traits
      (update :applicable-traits conj (trait/ability-score-increase stat/CON 1))
      (update :feature-traits conj :stout-resilience)))
