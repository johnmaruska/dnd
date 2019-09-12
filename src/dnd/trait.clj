(ns dnd.trait
  (:require [clojure.set :refer [union]]
            [dnd.armor :as armor]
            [dnd.armor.category :as armor.category]
            [dnd.skill :as skill]
            [dnd.stat :refer [increase-ability-score]]
            [dnd.trait.applicable :as applicable]
            [dnd.weapon :as weapon]))

(defn apply-single-trait [player trait]
  ((:apply trait) player))

(defn apply-all [player traits]
  (reduce apply-single-trait player traits))

(def ability-score-increase applicable/ability-score-increase)
(def drow-weapon-training applicable/drow-weapon-training)
(def dwarven-armor-training applicable/dwarven-armor-training)
(def dwarven-combat-training applicable/dwarven-armor-training)
(def elf-weapon-training applicable/elf-weapon-training)
(def fleet-of-foot applicable/fleet-of-foot)
(def keen-senses applicable/keen-senses)
(def menacing applicable/menacing)
(def tinker applicable/tinker)

;;; Dwarf - PHB 18-20
;; TODO: Darkvision
;; TODO: Dwarven Resilience
;; TODO: Stonecunning
;; TODO: Dwarven Toughness

;; Elf - PHB 21-24
;; TODO: Darkvision
;; TODO: Mask of the Wild
;; TODO: Fey Ancestry
;; TODO: Trance
;; TODO: Superior Darkvision
;; TODO: Sunlight Sensitivity
;; TODO: Drow Magic
;; TODO: Cantrip

;;; Halfling - PHB 26-28
;; TODO: Halfling Nimbleness
;; TODO: Brave
;; TODO: Lucky
;; TODO: Naturally Stealthy
;; TODO: Stout Resilience

;;; Dragonborn - PHB 32-34
;; TODO: Breath Weapon (based on draconic ancestry)
;; TODO: Damage Resistance (based on draconic ancestry)

;; Gnome - PHB 35-37
;; TODO: Darkvision
;; TODO: Gnome Cunning
;; TODO: Artificer's Lore
;; TODO: Tinker
;; TODO: Natural Illusionist
;; TODO: Speak with Small Beasts

;;; Half-Orc - PHB 40-21
;; TODO: Relentless Endurance
;; TODO: Savage Attacks

;;; Tiefling - PHB 42-43
;; TODO: Hellish Resistance
;; TODO: Infernal Legacy
