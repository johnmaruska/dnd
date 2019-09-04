(ns dnd.race.gnome
  (:require [dnd.stat :as stat]
            [dnd.trait :as trait]))

(def ^:private traits
  {:alignments [alignment/lawful alignment/neutral alignment/chaotic alignment/good]
   :applicable-traits [(trait/ability-score-increase stat/INT 2)]
   :base-speed 25
   :feature-traits #{:darkvision :gnome-cunning}
   :height {:base {:feet 2 :inches 11} :modifier {:d4 2}}
   :languages #{language/common language/gnomish}
   :size :small
   :weight {:base 35 :modifier nil}
   :age {:maturity 40 :lifespan 500}})

(def forest-gnome-traits
  (-> traits
      (update :applicable-traits cons (trait/ability-score-increase stat/DEX 1))
      (update :feature-traits union #{:natural-illusionist
                                      :speak-with-small-beasts})))

(def rock-gnome-traits
  (-> traits
      (update :applicable-traits cons (trait/ability-score-increase stat/CON 1))
      (update :feature-traits union #{:artificers-lore})))
