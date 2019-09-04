(ns dnd.race.half-orc
  (:require [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.stat :as stat]
            [dnd.trait :as trait]))

(def traits
  {:alignments [alignment/chaotic alignment/evil alignment/neutral]
   :applicable-traits [(trait/ability-score-increase stat/STR 2)
                       (trait/ability-score-increase stat/CON 1)
                       trait/menacing]
   :base-speed 30
   :feature-traits #{:darkvision :relentless-endurance :savage-attacks}
   :height {:base {:feet 4 :inches 10} :modifier {:d10 2}}
   :languages #{language/common language/orc}
   :size :medium
   :weight {:base 140 :modifier {:d6 2}}
   :age {:maturity 14 :lifespan 75}})
