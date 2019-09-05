(ns dnd.weapon
  (:require [dnd.dice :as dice]
            [dnd.player :as player]
            [dnd.stat :as stat]))

(def example-weapon
  {:name :sword
   :properties [:light :finesse :thrown]
   :type :melee})


(def battleaxe :battleaxe)
(def hand-crossbow :hand-crossbow)
(def handaxe :handaxe)
(def longbow :longbow)
(def longsword :longsword)
(def rapier :rapier)
(def shortbow :shortbow)
(def shortsword :shortsword)
(def throwing-hammer :throwing-hammer)
(def warhammer :warhammer)

(defn finesse? [weapon] (contains? (:properties weapon) :finesse))

(defn thrown? [weapon] (contains? (:properties weapon) :thrown))

(defn melee? [weapon] (= :melee (:type weapon)))

(defn ranged? [weapon] (= :ranged (:type weapon)))

;; Player's Handbook Ch1.5 page 14
(defn melee-bonus
  [player weapon]
  {:pre [(= :melee (:type weapon))]}
  (if (finesse? weapon)
    (max [(player/dex-modifier player)
          (player/str-modifier player)])
    (player/str-modifier player)))

;; Player's Handbook Ch1.5 page 14
(defn ranged-bonus
  [player weapon]
  {:pre [(= :ranged (:type weapon))]}
  (if (thrown? weapon)
    (max [(player/dex-modifier player)
          (player/str-modifier player)])
    (player/dex-modifier player)))

;; Player's Handbook Ch1.5 page 14
(defn attack [player weapon]
  (+ (dice/roll :d20)
     ;; TODO: extract relevant piece from weapon for proficient?
     (if (player/proficient? player weapon)
       (:proficiency-bonus player)
       0)
     (if (melee? weapon)
       (melee-bonus player weapon)
       (ranged-bonus player weapon))))
