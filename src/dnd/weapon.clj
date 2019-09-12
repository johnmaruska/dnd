(ns dnd.weapon
  (:refer-clojure :exclusions [get])
  (:require [clojure.set :refer [union]]
            [dnd.dice :as dice :refer [d4 d6 d8 d10 d12]]
            [dnd.player :as player]
            [dnd.stat :as stat]
            [dnd.weapon.martial :as martial]
            [dnd.weapon.simple :as simple]
            [dnd.weapon.util :refer [finesse? thrown? melee?]]))

(def ^:private table
  (->> (clojure.set/union martial/all simple/all)
       (map #([(:name weapon) weapon]))
       (into {})))

(defn get [weapon-name]
  (table weapon-name))

(defn proficient? [player weapon]
  (contains? (get-in player [:proficiencies :weapon])
             (:name weapon)))

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
  {:pre [(or (= :ranged (:type weapon)) (thrown? weapon))]}
  (if (thrown? weapon)
    (max [(player/dex-modifier player)
          (player/str-modifier player)])
    (player/dex-modifier player)))

;; Player's Handbook Ch1.5 page 14
(defn attack [player weapon]
  (+ (dice/roll :d20)
     ;; TODO: extract relevant piece from weapon for proficient?
     (if (proficient? player weapon)
       (:proficiency-bonus player)
       0)
     (if (melee? weapon)
       (melee-bonus player weapon)
       (ranged-bonus player weapon))))
