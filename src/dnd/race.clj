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
(defn elf? [character]  ;; are half-elves classified as elves?
  (contains? [dark-elf high-elf wood-elf] (:race character)))

(def lightfoot-halfling :lightfoot-halfling)
(def stout-halfling :stout-halfling)

(def human :human)
(def dragonborn :dragonborn)
(def forest-gnome :forest-gnome)
(def rock-gnome :rock-gnome)
(def half-elf :half-elf)
(def half-orc :half-orc)
(def tiefling :tiefling)


(defn get-traits [race]
  (condp = race  ;; `condp =` instead of `case` so we can use symbol
    ;; dwarf
    hill-dwarf          dwarf/hill-dwarf-traits
    mountain-dwarf      dwarf/mountain-dwarf-traits
    ;; elf
    dark-elf            elf/dark-elf-traits
    high-elf            elf/high-elf-traits
    wood-elf            elf/wood-elf-traits
    ;; halfling
    lightfoot-halfling  halfling/lightfoot-halfling
    stout-halfling      halfling/stout-halflight
    ;; half-elf
    half-elf            half-elf/traits
    ;; half-orc
    half-orc            half-orc/traits
    ;; human
    human               human/traits
    ;; variants
    :human-with-feat    human/variant-trait-feat
    :human-with-skills  human/variant-traits-skill
    :human-with-ability human/variant-traits-ability-score
    ;; Dragonborn
    dragonborn          dragonborn/traits
    ;; Gnome
    forest-gnome        gnome/forest-gnome-traits
    rock-gnome          gnome/rock-gnome-traits
    ;; Tiefling
    tiefling            tiefling/traits))

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


(defn- roll-modifier [modifier]
  (let [[die rolls] (first modifier)]
    (repeatedly rolls (dice/roll die))))

(defn with-random-height-and-weight
  "Give a random height and weight to a player character which already has a race."
  [player]
  (let [{:keys [base-height height-mod]} (:height (get-traits race))
        {:keys [base-weight weight-mod]} (:weight (get-traits race))
        height-roll (roll-modifier height-mod)
        weight-roll (roll-modifier weight-mod)]
    (assoc player
           :height (+ base-height height-roll)
           :weight (+ base-weight (* height-roll weight-roll)))))
