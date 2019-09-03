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
   :features-traits #{:darkvision
                      :fey-ancestry
                      :trance}
   :applicable-traits [(trait/ability-score-increase stat/DEX 2)
                       trait/keen-senses]
   :choosable-traits []})

(def high-elf-traits
  (-> traits
      (update :applicable-traits concat [trait/elf-weapon-training
                                         (trait/ability-score-increase stat/INT 1)])
      (update :features-traits union #{:cantrip})
      (update :choosable-traits concat [{:extra-language (set language/all)}])))

(def wood-elf-traits
  (-> traits
      (assoc :base-speed 35)  ; Fleet Footed
      (update :applicable-traits concat [(trait/ability-score-increase stat/WIS 1)
                                         trait/elf-weapon-training])
      (update :features-traits union #{:mask-of-the-wild})))

(def dark-elf-traits
  (-> traits
      (update :alignments difference #{alignment/good})
      (update :alignments union #{alignment/evil})
      (update :applicable-traits concat [(trait/ability-score-increase stat/CHA 1)
                                         trait/drow-weapon-training])
      (update :features-traits union #{:superior-darkvision
                                       :sunlight-sensitivity
                                       :drow-magic})))
