(ns dnd.player
  (:require
   [clojure.set :refer [union]]
   [dnd.stat :as stat]
   [dnd.util :as util]))

(def blank
  {:level         1
   :experience    0
   :feats         #{}
   :languages     #{}
   :proficiencies {:skill #{}
                   :armor #{}}})

(defn set-level [player level]
  (-> player
      (assoc :level (min 20 (max 1 level)))))

(defn level-up [player]
  (set-level player (inc (:level player))))

(defn add-proficiency [player type proficiency]
  (update-in player [:proficiencies type] union #{proficiency}))

(defn proficient? [player type proficiency]
  (contains? (get-in player [:proficiencies type]) proficiency))

(defn add-language [player proficiency]
  (update player :languages union #{proficiency}))

(defn add-feat [player feat]
  (update player :feats union #{feat}))

;; Player's Handbook Ch1.3 page 13
(defn- get-ability-modifier
  "Retrieve derived trait "
  [player ability]
  ;; magic numbers derived from tables provided in PHB
  (-> (get-in player [:ability-scores ability])
      (- 10)
      (util/div 2)))
(defn cha-modifier [player] (get-ability-modifier player stat/CHA))
(defn con-modifier [player] (get-ability-modifier player stat/CON))
(defn dex-modifier [player] (get-ability-modifier player stat/DEX))
(defn int-modifier [player] (get-ability-modifier player stat/INT))
(defn str-modifier [player] (get-ability-modifier player stat/STR))
(defn wis-modifier [player] (get-ability-modifier player stat/WIS))

(defn proficiency-bonus [player]
  (-> (:level player)
      (- 1)
      (util/div 4)
      (+ 2)))
