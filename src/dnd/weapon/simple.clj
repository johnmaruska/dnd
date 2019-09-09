(ns dnd.weapon.simple
  (:require
   [dnd.dice :refer [d4 d6 d8]]
   [dnd.weapon.util :as util :refer [ammunition
                                     bludgeoning
                                     damage
                                     finesse
                                     light
                                     piercing
                                     slashing
                                     thrown
                                     two-handed
                                     versatile]]))

;;;; Melee Weapons

(defmacro defmelee [name weapon]
  `(def ~name
     (-> ~weapon
         (assoc :name ~name
                :category util/simple
                :type util/melee)
         (update :damage #(apply damage %1)))))

(defmelee club
  {:cost       {:silver 1}
   :damage     [1 d4 bludgeoning]
   :weight     2
   :properties [light]})
(defmelee dagger
  {:cost       {:gold 2}
   :damage     [1 d4 piercing]
   :weight     1
   :properties [finesse, light, (thrown 20 60)]})
(defmelee greatclub
  {:cost       {:silver 2}
   :damage     [1 d8 bludgeoning]
   :weight     10
   :properties [two-handed]})
(defmelee handaxe
  {:cost       {:gold 5}
   :damage     [1 d6 slashing]
   :weight     2
   :properties [light, (thrown 20 60)]})
(defmelee javelin
  {:cost       {:silver 5}
   :damage     [1 d6 piercing]
   :weight     2
   :properties [(thrown 30 120)]})
(defmelee light-hammer
  {:cost       {:gold 2}
   :damage     [1 d4 bludgeoning]
   :weight     2
   :properties [light (thrown 20 60)]})
(defmelee mace
  {:cost       {:gold 5}
   :damage     [1 d6 bludgeoning]
   :weight     4
   :properties []})
(defmelee quarterstaff
  {:cost       {:silver 2}
   :damage     [1 d6 bludgeoning]
   :weight     4
   :properties [(versatile 1 d8)]})
(defmelee sickle
  {:cost       {:gold 1}
   :damage     [1 d4 slashing]
   :weight     2
   :properties [light]})
(defmelee spear
  {:cost       {:gold 1}
   :damage     [1 d6 piercing]
   :weight     3
   :properties [(thrown 20 60) (versatile 1 d8)]})
(defmelee unarmed-strike
  {:cost       {}
   :damage     [1 bludgeoning]
   :weight     0
   :properties []})

;;;; Ranged Weapons

(defmacro defranged [name weapon]
  `(def ~name
     (-> ~weapon
         (assoc :name (keyword ~name)
                :category util/simple
                :type util/ranged)
         (update :damage #(apply damage %1)))))

(defranged light-crossbow
  {:cost       {:gold 25}
   :damage     [1 d8 piercing]
   :weight     5
   :properties [(ammunition 80 320) loading two-handed]})
(defranged dart
  {:cost       {:copper 5}
   :damage     [1 d4 piercing]
   :weight     1/4
   :properties [finesse (thrown 20 60)]})
(defranged shortbow
  {:cost       {:gold 25}
   :damage     [1 d6 piercing]
   :weight     2
   :properties [(ammunition 80 320) two-handed]})
(defranged sling
  {:cost       {:silver 1}
   :damage     [1 d4 bludgeoning]
   :weight     0
   :properties [(ammunition 30 120)]})

;;;; Collections

(def melee #{club dagger greatclub handaxe javelin light-hammer mace
             quarterstaff sickle spear unarmed-strike})

(def ranged #{light-crossbow dart shortbow sling})

(def all (clojure.set/union melee ranged))
