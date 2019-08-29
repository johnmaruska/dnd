(ns dnd.race
  (:require [dnd.race.dwarf :as dwarf]))

(def dwarf :dwarf)
(def hill-dwarf :hill-dwarf)
(def mountain-dwarf :mountain-dwarf)

(defn get-traits [race]
  (case race
    :hill-dwarf dwarf/hill-dwarf-traits
    :mountain-dwarf dwarf/mountain-dwarf-traits))

(defn apply-traits [player traits]
  (reduce (fn [p [_ apply-trait]] (apply-trait p))
          player
          (:applicable-traits traits)))

(def player-chosen-characteristics [:age :height :name])

(defn apply-racial-traits [player]
  (if (not (:subrace player))
    (-> player
        (merge (select-keys traits [:race :size :base-speed :languages
                                    :features-traits :choosable-traits]))
        (apply-traits racial-traits))
    player))

(defn with-race [player race]
  (apply-racial-traits player (get-traits race)))
