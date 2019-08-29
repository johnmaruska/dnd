(ns dnd.race.halfling
  (:require [dnd.stat :as stat]
            [dnd.trait :as trait]))

;; Player's Handbook Ch2 - Halfling page 26
(def ^:private traits
  {:race :halfling
   :age {:maturity 20
         :lifespan 250}
   :size {:class :small
          :low-end 3
          :high-end 3
          :estimated-weight 40}
   :physical-characteristics {:short-height 3
                              :tall-height 3
                              :estimated-weight 40}
   :base-speed 25
   :alignments [alignment/lawful alignment/good]
   :languages #{language/common language/halfling}
   :applicable-traits [(trait/ability-score-increase stat/DEX 2)]
   :choosable-traits []
   :features-traits #{:lucky :brave :halfling-nimbleness}})
