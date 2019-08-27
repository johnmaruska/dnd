(ns dnd.stat
  (:refer-clojure :rename {+ core-add
                           inc core-inc})
  (:require [dnd.dice :as dice]))

(def CHA :charisma)
(def CON :constitution)
(def DEX :dexterity)
(def INT :intelligence)
(def STR :strength)
(def WIS :wisdom)


(defn + [& args]
  (min 30 (apply core-add args)))
(defn inc [n]
  (min 30 (core-inc n)))

(defn update-ability-score
  [player stat value]
  (assoc-in player [:ability-scores stat] value))

;; TODO: ability score point cost
;; Player's Handbook Ch1.3 page 13
(defn apply-custom-scores
  "If a character has no ability scores, apply custom scores as provided."
  [player custom-stats]
  {:pre [(map? custom-stats)
         (= 6 (count custom-stats))]}
  (if (not (:ability-scores player))
    (merge player custom-stats)
    player))

;; Player's Handbook Ch1.3 page 13
(defn apply-standard-scores
  "If a character has no ability scores, apply standard scores in priority order."
  [player stat-prio-order]
  {:pre [(= 6 (count stat-prio-order))]}
  (if (not (:ability-scores player))
    (let [stat-val-pairs (map vector stat-prio-order [15 14 13 12 10 8])]
      (reduce (fn [p [s v]]
                (update-ability-score p s v))
              player
              stat-val-pairs))
    player))

(defn apply-random-scores
  [player stat-prio-order]
  (if (not (:ability-scores player))
    (let [roll-stat (fn [] (->> (repeatedly 4 (dice/roll :d6))
                                (sort >)
                                (take 3)
                                (reduce +)))]
      (->> (repeatedly 6 roll-stat)
           (sort >)
           (map vector stat-prio-order)
           (into {})
           (apply-custom-scores player)))
    player))
