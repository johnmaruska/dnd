(ns dnd.race.elf
  (:require [clojure.set :refer [difference union]]
            [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.stat :as stat]
            [dnd.trait :as trait]))

(def traits
  {:race :elf
   :age {:maturity 100 :lifespan 750}
   :height {:short 5 :tall 6}
   :weight {:average 150}
   :size :medium
   :alignments #{alignment/chaotic alignment/good}
   :base-speed 30
   :languages #{language/common language/elvish}
   :feature-traits #{:darkvision
                     :fey-ancestry
                     :trance}
   :applicable-traits [(trait/ability-score-increase stat/DEX 2)
                       trait/keen-senses]
   :choosable-traits []})

(def high-elf-traits
  (-> traits
      (assoc :subrace :high-elf
             :height {:base {:feet 4 :inches 6} :modifier {:d10 2}}
             :weight {:base 90 :modifier {:d4 1}})
      (update :applicable-traits concat [trait/elf-weapon-training
                                         (trait/ability-score-increase stat/INT 1)])
      (update :feature-traits union #{:cantrip})
      (update :choosable-traits conj trait/extra-language)))

(def wood-elf-traits
  (-> traits
      (assoc :subrace :wood-elf
             :height {:base {:feet 4 :inches 6} :modifier {:d10 2}}
             :weight {:base 100 :modifier {:d4 1}})
      (update :applicable-traits concat [(trait/ability-score-increase stat/WIS 1)
                                         trait/elf-weapon-training
                                         trait/fleet-of-foot])
      (update :feature-traits union #{:mask-of-the-wild})))

(def dark-elf-traits
  (-> traits
      (assoc :subrace :dark-elf
             :height {:base {:feet 4 :inches 5} :modifier {:d6 2}}
             :weight {:base 75 :modifier {:d6 1}})
      (update :alignments difference #{alignment/good})
      (update :alignments union #{alignment/evil})
      (update :applicable-traits concat [(trait/ability-score-increase stat/CHA 1)
                                         trait/drow-weapon-training])
      (update :feature-traits union #{:superior-darkvision
                                      :sunlight-sensitivity
                                      :drow-magic})))
