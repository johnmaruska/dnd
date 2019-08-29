(ns dnd.race
  (:require [dnd.race.dwarf :as dwarf]
            [dnd.race.elf :as elf]))

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

(defn get-traits [race]
  (case race
    ;; dwarf
    hill-dwarf dwarf/hill-dwarf-traits
    mountain-dwarf dwarf/mountain-dwarf-traits
    ;; elf
    dark-elf elf/drow-traits
    high-elf elf/high-elf-traits
    wood-elf elf/wood-elf-traits
    ))

(defn apply-traits [player traits]
  (reduce (fn [p [_ apply-trait]] (apply-trait p))
          player
          (:applicable-traits traits)))

(def player-chosen-characteristics [:age :height :weight :name])

(defn apply-racial-traits [player traits]
  (if (not (:subrace player))
    (-> player
        (merge (select-keys traits [:race :size :base-speed :languages
                                    :features-traits :choosable-traits]))
        (apply-traits traits))
    player))

(defn with-race [player race]
  (apply-racial-traits player (get-traits race)))
