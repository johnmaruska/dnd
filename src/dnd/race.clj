(ns dnd.race
  (:require
   [dnd.dice :as dice]
   [dnd.race.dragonborn :as race.dragonborn]
   [dnd.race.dwarf :as race.dwarf]
   [dnd.race.elf :as race.elf]
   [dnd.race.gnome :as race.gnome]
   [dnd.race.half-elf :as race.half-elf]
   [dnd.race.halfling :as race.halfling]
   [dnd.race.half-orc :as race.half-orc]
   [dnd.race.human :as race.human]
   [dnd.race.tiefling :as race.tiefling]
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

(def all [hill-dwarf
          mountain-dwarf
          dark-elf
          high-elf
          wood-elf
          lightfoot-halfling
          stout-halfling
          human
          dragonborn
          forest-gnome rock-gnome
          half-elf
          half-orc
          tiefling])

(defn get-traits [race]
  (condp = race  ;; `condp =` instead of `case` so we can use symbol
    hill-dwarf          race.dwarf/hill-dwarf-traits
    mountain-dwarf      race.dwarf/mountain-dwarf-traits
    dark-elf            race.elf/dark-elf-traits
    high-elf            race.elf/high-elf-traits
    wood-elf            race.elf/wood-elf-traits
    lightfoot-halfling  race.halfling/lightfoot-halfling-traits
    stout-halfling      race.halfling/stout-halfling-traits
    half-elf            race.half-elf/traits
    half-orc            race.half-orc/traits
    human               race.human/traits
    :human-with-feat    race.human/variant-trait-feat
    :human-with-skills  race.human/variant-traits-skills
    :human-with-ability race.human/variant-traits-ability-score
    dragonborn          race.dragonborn/traits
    forest-gnome        race.gnome/forest-gnome-traits
    rock-gnome          race.gnome/rock-gnome-traits
    tiefling            race.tiefling/traits))

(def player-chosen-characteristics [:age :height :weight :name])

(defn apply-racial-traits [player traits]
  (if (not (:subrace player))
    (-> player
        (merge (select-keys traits [:race :size :subrace :base-speed :languages
                                    :feature-traits :choosable-traits]))
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
  {:pre [(not (nil? (:race player)))]}
  (let [{:keys [base-height height-mod]} (:height (get-traits (:race player)))
        {:keys [base-weight weight-mod]} (:weight (get-traits (:race player)))
        height-roll (roll-modifier height-mod)
        weight-roll (roll-modifier weight-mod)]
    (assoc player
           :height (+ base-height height-roll)
           :weight (+ base-weight (* height-roll weight-roll)))))
