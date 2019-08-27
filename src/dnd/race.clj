(ns dnd.race
  (:require [dnd.race.dwarf :as dwarf]))

(def dwarf :dwarf)
(def hill-dwarf :hill-dwarf)
(def mountain-dwarf :mountain-dwarf)

(defn with-race [player race]
  (case race
    ;; dwarves
    :hill-dwarf (dwarf/apply-hill-dwarf player)
    :mountain-dwarf (dwarf/apply-mountain-dwarf player)))
