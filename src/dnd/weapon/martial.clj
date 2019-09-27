(ns dnd.weapon.martial
  (:require
   [clojure.set :refer [union]]
   [dnd.dice :refer [d4 d6 d8 d10 d12]]
   [dnd.weapon.util :as util :refer [ammunition
                                     bludgeoning
                                     damage
                                     finesse
                                     heavy
                                     light
                                     loading
                                     piercing
                                     reach
                                     slashing
                                     special
                                     thrown
                                     two-handed
                                     versatile]]))

;;;; Melee Weapons

(defmacro defmelee [wname weapon]
  `(def ~wname
     (-> ~weapon
         (assoc :name (keyword (name '~wname))
                :category util/martial
                :type util/melee)
         (update :damage #(apply damage %1)))))

(defmelee battleaxe
  {:cost       {:gold 10}
   :damage     [1 d8 slashing]
   :weight     4
   :properties [(versatile 1 d10)]})

(defmelee flail
  {:cost       {:gold 10}
   :damage     [1 d8 bludgeoning]
   :weight     2
   :properties []})

(defmelee glaive
  {:cost       {:gold 20}
   :damage     [1 d10 slashing]
   :weight     6
   :properties [heavy reach two-handed]})

(defmelee greataxe
  {:cost       {:gold 30}
   :damage     [1 d12 slashing]
   :weight     7
   :properties [heavy two-handed]})

(defmelee greatsword
  {:cost       {:gold 50}
   :damage     [2 d6 slashing]
   :weight     6
   :properties [heavy two-handed]})

(defmelee halberd
  {:cost       {:gold 20}
   :damage     [1 d10 slashing]
   :weight     6
   :properties [heavy reach two-handed]})

(defmelee lance
  {:cost       {:gold 10}
   :damage     [1 d12 piercing]
   :weight     6
   :properties [reach special]})

(defmelee longsword
  {:cost       {:gold 15}
   :damage     [1 d8 slashing]
   :weight     3
   :properties [(versatile 1 d10)]})

(defmelee maul
  {:cost       {:gold 10}
   :damage     [2 d6 bludgeoning]
   :weight     10
   :properties [heavy two-handed]})

(defmelee morningstar
  {:cost       {:gold 15}
   :damage     [1 d8 piercing]
   :weight     4
   :properties []})

(defmelee pike
  {:cost       {:gold 5}
   :damage     [1 d10 piercing]
   :weight     18
   :properties [heavy reach two-handed]})

(defmelee rapier
  {:cost       {:gold 25}
   :damage     [1 d8 piercing]
   :weight     2
   :properties [finesse]})

(defmelee scimitar
  {:cost       {:gold 25}
   :damage     [1 d6 slashing]
   :weight     3
   :properties [finesse light]})

(defmelee shortsword
  {:cost       {:gold 10}
   :damage     [1 d6 piercing]
   :weight     2
   :properties [finesse light]})

(defmelee trident
  {:cost       {:gold 5}
   :damage     [1 d6 piercing]
   :weight     4
   :properties [(thrown 20 60) (versatile 1 d8)]})

(defmelee war-pick
  {:cost       {:gold 5}
   :damage     [1 d8 piercing]
   :weight     2
   :properties []})

(defmelee warhammer
  {:cost       {:gold 15}
   :damage     [1 d8 bludgeoning]
   :weight     2
   :properties [(versatile 1 d10)]})

(defmelee whip
  {:cost       {:gold 2}
   :damage     [1 d4 slashing]
   :weight     3
   :properties [finesse reach]})

;;;; Ranged Weapons

(defmacro defranged [name weapon]
  `(def ~name
     (-> ~weapon
         (assoc :name ~name
                :category util/martial
                :type util/ranged)
         (update :damage #(apply damage %1)))))

(defranged blowgun
  {:cost {:gold 10}
   :damage [1 piercing]
   :weight 1
   :properties [(ammunition 25 100) loading]})

(defranged hand-crossbow
  {:cost {:gold 75}
   :damage [1 d6 piercing]
   :weight 3
   :properties [(ammunition 30 120) light loading]})

(defranged heavy-crossbow
  {:cost {:gold 50}
   :damage [1 d10 piercing]
   :weight 18
   :properties [(ammunition 100 400) heavy loading two-handed]})

(defranged longbow
  {:cost {:gold 50}
   :damage [1 d8 piercing]
   :weight 2
   :properties [(ammunition 150 600) heavy two-handed]})

(defranged net
  {:cost       {:gold 1}
   :damage     nil
   :weight     3
   :properties [special (thrown 5 15)]})

;;;; Collections

(def melee #{battleaxe flail glaive greataxe greatsword halberd lance longsword
             maul morningstar pike rapier scimitar shortsword trident war-pick
             warhammer whip})

(def ranged #{blowgun hand-crossbow heavy-crossbow longbow net})

(def all (union melee ranged))
