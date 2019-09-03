(ns dnd.race.halfling
  (:require [dnd.stat :as stat]
            [dnd.trait :as trait]))

;; Player's Handbook Ch2 - Halfling page 26
(def ^:private traits
  {:age {:maturity 20 :lifespan 250}
   :alignments [alignment/lawful alignment/good]
   :applicable-traits [(trait/ability-score-increase stat/DEX 2)]
   :base-speed 25
   :choosable-traits []
   :features-traits #{:lucky :brave :halfling-nimbleness}
   :height {:short 3 :tall 3}
   :languages #{language/common language/halfling}
   :size :small
   :weight {:average 40}
   :race :halfling})

(def lightfoot-halfling-traits
  (-> traits
      (update :applicable-traits
              (partial cons (trait/ability-score-increase stat/CHA 2)))
      (update :feature-traits
              (partial cons :naturally-stealthy))))
