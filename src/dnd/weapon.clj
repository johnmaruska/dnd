(ns dnd.weapon)

(def example-weapon
  {:properties [:light :finesse :thrown]
   :type :melee})


(def battleaxe :battleaxe)
(def handaxe :handaxe)
(def throwing-hammer :throwing-hammer)
(def warhammer :warhammer)


(defn finesse? [weapon]
  (contains? (:properties weapon) :finesse))

(defn thrown? [weapon]
  (contains? (:properties weapon) :thrown))

;; Player's Handbook Ch1.5 page 14
(defn melee-bonus
  [player weapon]
  {:pre [(= :melee (:type weapon))]}
  (if (finesse? weapon)
    (max [(player/get-ability-modifier player stat/DEX)
          (player/get-ability-modifier player stat/STR)])
    (player/get-ability-modifier player stat/STR)))

;; Player's Handbook Ch1.5 page 14
(defn ranged-bonus
  [player weapon]
  {:pre [(= :ranged (:type weapon))]}
  (if (thrown? weapon)
    (max [(player/get-ability-modifier player stat/DEX)
          (player/get-ability-modifier player stat/STR)])
    (player/get-ability-modifier player stat/DEX)))

;; Player's Handbook Ch1.5 page 14
(defn attack [player weapon]
  (+ (dice/roll :d20)
     ;; TODO: extract relevant piece from weapon for proficient?
     (if (player/proficient? player weapon)
       (:proficiency-bonus player)
       0)
     (if (weapon/melee? weapon)
       (melee-bonus player weapon)
       (ranged-bonus player weapon))))
