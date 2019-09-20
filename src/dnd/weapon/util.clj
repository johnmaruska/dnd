(ns dnd.weapon.util
  (:refer-clojure :exclude [range]))

(def simple :simple)
(def martial :martial)
(def categories #{simple martial})

;; weapon type
(def melee :melee)
(def ranged :ranged)

;; damage type
(def bludgeoning :bludgeoning)
(def piercing :piercing)
(def slashing :slashing)

;; weapon properties
(defn- range [normal maximum]
  {:normal normal :maximum maximum})

(defn ammunition [normal maximum]
  {:property :ammunition :range (range normal maximum)})

(def finesse :finesse)
(def heavy   :heavy)
(def light   :light)
(def loading :loading)
(def reach   :reach)
(def special :special)  ;; TODO: rules on PHB p148

(defn thrown [normal maximum]
  {:property :thrown
   :range (range normal maximum)})
(def two-handed :two-handed)
(defn versatile [tot-dice dice-type]
  {:property :versatile
   :dice {dice-type tot-dice}})

(defn damage
  ([]
   {:amount 0})
  ([amount damage-type]
   {:amount amount
    :type damage-type})
  ([tot-dice dice-type damage-type]
   {:dice {dice-type tot-dice}
    :type damage-type}))


(defn finesse? [weapon] (contains? (:properties weapon) finesse))

(defn thrown? [weapon]
  (some #(when (map? %1) (= :thrown (:property %1)))
        (:properties weapon)))

(defn melee? [weapon] (= :melee (:type weapon)))
(defn ranged? [weapon] (= :ranged (:type weapon)))
