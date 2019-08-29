(ns dnd.race.elf
  (:require [clojure.set :refer [difference union]]
            [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.skill :as skill]
            [dnd.stat :as stat :refer [increase-ability-score]]
            [dnd.weapon :as weapon]))

(defn apply-elf-weapon-training [player]
  (update player :proficiencies (partial union #{weapon/longsword
                                                 weapon/shortsword
                                                 weapon/shortbow
                                                 weapon/longbow})))

(defn apply-drow-weapon-training [player]
  (update player :proficiencies (partial union #{weapon/rapier
                                                 weapon/shortsword
                                                 weapon/hand-crossbow})))

(defn apply-ability-score-increase [stat val]
  (fn [player]
    (increase-ability-score player stat val)))

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
   :applicable-traits [{:ability-score-increase #(increase-ability-score % stat/DEX 2)}
                       {:keen-senses #(update % :proficiencies
                                              (partial union #{skill/perception}))}]})

(def high-elf-traits
  (-> traits
      (update :applicable-traits
              (partial concat [{:elf-weapon-training apply-elf-weapon-training}
                               {:ability-score-increase #(increase-ability-score % stat/INT 1)}]))
      (update :features-traits (partial union #{:cantrip}))
      (update :choosable-traits [{:extra-language (set language/all)}])))

(def wood-elf-traits
  (-> traits
      (assoc :base-speed 35)
      (update :applicable-traits
              (partial concat [{:ability-score-increase #(increase-ability-score % stat/WIS 1)}
                               {:elf-weapon-training apply-elf-weapon-training}]))
      (update :features-traits (partial union #{:mask-of-the-wild}))))

(def dark-elf-traits
  (-> traits
      (update :alignments #(difference % #{alignment/good}))
      (update :alignments #(union % #{alignment/evil}))
      (update :applicable-traits
              (partial concat [{:ability-score-increase #(increase-ability-score % stat/CHA 1)}
                               {:drow-weapon-training apply-drow-weapon-training}]))
      (update :features-traits (partial union #{:superior-darkvision
                                                :sunlight-sensitivity
                                                :drow-magic}))))
