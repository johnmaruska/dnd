(ns dnd.race.gnome
  (:require [dnd.stat :as stat]
            [dnd.trait :as trait]))

(def ^:private traits
  {:age {:maturity 40 :lifespan 500}
   :applicable-traits [(trait/ability-score-increase stat/INT 2)]
   ;; TODO: separate into moral and legal?
   :alignments [alignment/lawful alignment/neutral alignment/chaotic alignment/good]
   :size :small
   :height {:short 3 :tall 4}
   :weight {:average 40}
   :base-speed 25
   :feature-traits #{:darkvision :gnome-cunning}
   :languages #{language/common language/gnomish}})

(def forest-gnome-traits
  (-> traits
      (update :applicable-traits
              (partial cons (trait/ability-score-increase stat/DEX 1)))
      (update :feature-traits (partial union #{:natural-illusionist
                                               :speak-with-small-beasts}))))

(def rock-gnome-traits
  (-> traits
      (update :applicable-traits
              (partial cons (trait/ability-score-increase stat/CON 1)))
      (update :feature-traits (partial union #{:artificers-lore}))
      ))
