(ns dnd.race.elf
  (:require [clojure.set :refer [difference union]]
            [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.stat :as stat]
            [dnd.trait :as trait]))

(def traits
  {:race :elf
   :physical-characteristics {:mature-age 100
                              :max-age 750
                              :short-height 5
                              :tall-height 6
                              :estimated-weight 150}
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
      (update :applicable-traits
              (partial concat [trait/elf-weapon-training
                               (trait/ability-score-increase stat/INT 1)]))
      (update :features-traits (partial union #{:cantrip}))
      (update :choosable-traits (concat [{:extra-language (set language/all)}]))))

(def wood-elf-traits
  (-> traits
      (assoc :base-speed 35)  ; Fleet Footed
      (update :applicable-traits
              (partial concat [(trait/ability-score-increase stat/WIS 1)
                               trait/elf-weapon-training]))
      (update :features-traits (partial union #{:mask-of-the-wild}))))

(def dark-elf-traits
  (-> traits
      (update :alignments #(difference % #{alignment/good}))
      (update :alignments #(union % #{alignment/evil}))
      (update :applicable-traits
              (partial concat [(trait/ability-score-increase stat/CHA 1)
                               trait/drow-weapon-training]))
      (update :features-traits (partial union #{:superior-darkvision
                                                :sunlight-sensitivity
                                                :drow-magic}))))
