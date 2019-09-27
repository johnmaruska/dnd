(ns dnd.stat
  (:refer-clojure :rename {+ core-add
                           inc core-inc}
                  :exclude [max])
  (:require [dnd.dice :as dice :refer [d6]]))

(def CHA :charisma)
(def CON :constitution)
(def DEX :dexterity)
(def INT :intelligence)
(def STR :strength)
(def WIS :wisdom)
(def all [CHA CON DEX INT STR WIS])

(def standard-scores [15 14 13 12 10 8])
(def max-value 30)

(defn + [& args]
  (min max-value (or (apply core-add args) 0)))
(defn inc [n]
  (min max-value (core-inc n)))

(defn set-ability-score [player stat value]
  (assoc-in player [:ability-scores stat] value))
(defn increase-ability-score [player stat amount & {:keys [max]
                                                    :or {max max-value}}]
  (with-redefs [max-value max]
    (update-in player [:ability-scores stat] + amount)))

;; TODO: ability score point cost
;; Player's Handbook Ch1.3 page 13
(defn with-custom-scores
  "If a character has no ability scores, apply custom scores as provided."
  ([player custom-stats]
   {:pre [(map? custom-stats)
          (= 6 (count custom-stats))]}
   (if (not (:ability-scores player))
     (assoc player :ability-scores custom-stats)
     player))
  ([player stat-prio-order stat-vals]
   {:pre [(= 6 (count stat-vals))
          (= 6 (count stat-prio-order))]}
   (->> (map vector stat-prio-order stat-vals)
        (into {})
        (with-custom-scores player))))

;; Player's Handbook Ch1.3 page 13
(defn with-standard-scores
  "If a character has no ability scores, apply standard scores in priority order."
  [player stat-prio-order]
  {:pre [(= 6 (count stat-prio-order))]}
  (with-custom-scores player stat-prio-order standard-scores))

(defn with-random-scores
  ([player]
   (with-random-scores player (shuffle all)))
  ([player stat-prio-order]
   {:pre [(= 6 (count stat-prio-order))]}
   (let [roll-stat (fn [] (->> (dice/roll 4 d6)
                               (sort >)
                               (take 3)
                               (reduce +)))]
     (->> (repeatedly 6 roll-stat)
          (sort >)
          (with-custom-scores player stat-prio-order)))))
