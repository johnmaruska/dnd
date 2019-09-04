(ns dnd.race.tiefling)

(def traits
  {:age {:maturity 18 :lifespan 100}
   :applicable-traits [(trait/ability-score-increase stat/INT 1)
                       (trait/ability-score-increase stat/CHA 2)]
   :alignment [alignment/chaotic alignment/evil alignment/neutral]
   :size :medium
   :height {:base {:feet 4 :inches 9} :modifier {:d8 2}}
   :weight {:base {110 :modifier {:d4 2}}}
   :base-speed 30
   :feature-traits #{:darkvision :hellish-resistance :infernal-legacy}
   :languages #{language/common language/infernal}})
