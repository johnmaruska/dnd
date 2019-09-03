(ns dnd.race
  (:require [dnd.race.dwarf :as dwarf]
            [dnd.race.elf :as elf]
            [dnd.trait :as trait]))

(def hill-dwarf :hill-dwarf)
(def mountain-dwarf :mountain-dwarf)
(defn dwarf? [character]
  (contains? [hill-dwarf mountain-dwarf] (:race character)))

(def dark-elf :dark-elf)
(def drow dark-elf)
(def high-elf :high-elf)
(def wood-elf :wood-elf)
(defn elf? [character]
  (contains? [dark-elf high-elf wood-elf] (:race character)))

(def human :human)

(defn get-traits [race]
  (condp = race  ;; `condp =` instead of `case` so we can use symbol
    ;; dwarf
    hill-dwarf          dwarf/hill-dwarf-traits
    mountain-dwarf      dwarf/mountain-dwarf-traits
    ;; elf
    dark-elf            elf/dark-elf-traits
    high-elf            elf/high-elf-traits
    wood-elf            elf/wood-elf-traits
    ;; human
    human               human/traits
    ;; variants
    :human-with-feat    human/variant-trait-feat
    :human-with-skills  human/variant-traits-skill
    :human-with-ability human/variant-traits-ability-score
    ))

(def player-chosen-characteristics [:age :height :weight :name])

(defn apply-racial-traits [player traits]
  (if (not (:subrace player))
    (-> player
        (merge (select-keys traits [:race :size :subrace :base-speed :languages
                                    :features-traits :choosable-traits]))
        (trait/apply-all (:applicable-traits traits)))
    player))

(defn with-race [player race]
  (apply-racial-traits player (get-traits race)))
