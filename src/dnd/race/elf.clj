(ns dnd.race.elf
  (:require [clojure.set :refer [difference union]]))

(def traits
  {:mature-age 100
   :max-age 750
   :short-height 5
   :tall-height 6
   :size :medium
   :alignments #{alignment/chaotic alignment/good}
   :base-speed 30
   :features-and-traits #{:darkvision
                          :fey-ancestry
                          :trance}})



(def drow-elf-traits
  (-> traits
      (update :alignments #(difference % #{alignment/good}))
      (update :alignments #(union % #{alignment/evil}))))

(def high-elf-traits
  (-> traits
      ))

(defn apply-elf-weapon-training [player]
  (update player :proficiencies (partial concat [weapon/longsword
                                                 weapon/shortsword
                                                 weapon/shortbow
                                                 weapon/longbow])))

(defn apply-race [player]
  (if (not (:race player))
    (-> player
        (assoc :race :elf)
        (update-in [:ability-scores stat/DEX] (partial stat/+ 2))
        ;; skill proficiencies
        (update :proficiencies (partial conj skill/perception))
        (update :languages (partial concat [language/common language/elvish])))
    player))

(defn can-apply-subrace? [player]
  (let [race (:race player)]
    (and (or (nil? race) (= :elf race))
         (not (:subrace player)))))

(defn apply-high-elf [player]
  (if (can-apply-subrace? player)
    (-> player
        apply-race
        (update-in [:ability-scores stat/INT] stat/inc)
        ;; weapon proficiencies
        apply-elf-weapon-training
        (update :features-and-traits (partial union #{:cantrip
                                                      :extra-language})))
    player))

(defn apply-wood-elf [player]
  (if (can-apply-subrace? player)
    (-> player
        apply-race
        (update-in [:ability-score stat/WIS] stat/inc)
        apply-elf-weapon-training
        (update :features-and-traits (partial union #{:mask-of-the-wild})))
    player))

(defn apply-dark-elf [player]
  (if (can-apply-subrace? player)
    (-> player
        apply-race
        (update-in [:ability-score stat/CHA stat/inc])
        (update :features-and-traits (partial union #{:superior-darkvision
                                                      :sunlight-sensitivity
                                                      :drow-magic}))
        (update :proficiencies (partial concat [weapon/rapier
                                                weapon/shortsword
                                                weapon/hand-crossbow])))
    player))
(def apply-drow apply-dark-elf)
