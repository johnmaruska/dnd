(ns dnd.race.dragonborn
  (:require [clojure.set :refer [union]]
            [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.trait :as trait]
            [dnd.stat :as stat]))

(def ^:private traits
  {:height {:short 6 :tall 7}
   :weight {:average 250}
   :age {:maturity 15 :lifespan 80}
   :base-speed 30
   :alignment [alignment/good alignment/evil]
   :choosable-traits [{:draconic-ancestry #{:black :blue :brass :bronze :copper
                                            :gold :green :red :silver :white}}]
   ;; breath weapon depends on draconic ancestry, same damage-resistance
   :feature-traits #{:breath-weapon :damage-resistance}
   :applicable-traits [(trait/ability-score-increase stat/STR 2)
                       (trait/ability-score-increase stat/CHA 1)]
   :languages #{language/common language/draconic}})

(defn dragon-breath [dragon-flight]
  (let [line {:type line :width 5 :range 30}
        cone {:type cone :radius 15}]
    (case dragon-flight
      [:black :blue :brass :bronze :copper] {:save stat/DEX :area line}
      [:gold :red]                          {:save stat/DEX :area cone}
      [:green :silver :white]               {:save stat/CON :area cone})))

(def black-dragon-traits
  (-> traits
      (assoc :subrace :black-dragon)
      (update :feature-traits union #{:acid-resistance :dragon-breath})))
