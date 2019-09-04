(ns dnd.race.half-elf
  (:require [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.stat :as stat]
            [dnd.trait :as trait]))

(def traits
  {:age {:maturity 20 :lifespan 180}
   :alignments [alignment/chaotic alignment/good alignment/evil alignment/neutral]
   :applicable-traits [(trait/ability-score-increase stat/CHA 2)]
   :base-speed 30
   :choosable-traits [{:ability-score-increase stat/all}
                      {:ability-score-increase stat/all}
                      ;; Skill Versatility
                      {:skill-versatility skill/all}
                      {:skill-versatility skill/all}
                      ;; languages section
                      {:extra-language language/all}]
   :feature-traits [:darkvision :fey-ancestry]
   :height {:base {:feet 4 :inches 9} :modifier {:d8 2}}
   :languages #{language/common language/elvish}
   :size :medium
   :weight {:base 110 :modifier {:d4 2}}})
