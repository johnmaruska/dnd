(ns dnd.feat
  (:require
   [clojure.set :refer [union]]
   [dnd.armor.category :as armor.category]
   [dnd.player :as player]
   [dnd.stat :as stat]))

(defn meets-prereq? [player feat]
  (if-let [prereq (:prereq feat)]
    (prereq player)
    true))

(defn add-feat [player feat]
  (if (meets-prereq? player feat)
    (-> ((:apply feat) player)
        (update :feats union #{dissoc feat :apply :prereq}))
    player))

(def heavily-armored
  {:name   :heavily-armored
   :apply  #(-> (stat/increase-ability-score %1 stat/STR 1 :max 20)
                (player/add-proficiency :armor #{armor.category/heavy}))
   :prereq #(armor/proficient? %1 armor.category/medium)})

(defn- apply-lightly-armored [player]
  (let [stats [stat/DEX stat/STR]
        trait (chooseable-trait/ability-score-increase stats :max 20)]
    (-> (update player :choosable-traits conj trait)
        (player/add-proficiency :armor #{armor.category/light}))))

(def lightly-armored
  {:name :lightly-armored
   :apply apply-lightly-armored})

(defn- apply-moderately-armored [player]
    (let [stats [stat/DEX stat/STR]
          trait (chooseable-trait/ability-score-increase stats :max 20)]
    (-> (update player :choosable-traits conj trait)
        (player/add-proficiency :armor #{armor.category/medium})
        (player/add-proficiency :armor #{armor.category/shield}))))

(def moderately-armored
  {:name :moderately-armored
   :apply apply-moderately-armored
   :prereq #(armor/proficient? %1 armor.category/light)})

;; choosable trait resilient
(def trait/resilient
  {:name       :resilient
   :options    skill/all
   :applicable (fn [player stat-choice]
                 (-> player
                     (add-proficiency :saving-throw stat-choice)
                     (stat/increase-ability-score stat/choice 1 :max 20)))})

(def resilient
  {:name  :resilient
   :apply (update player :choosable-traits conj trait/resilient)})

;; choosable trait skilled
(defn trait/skilled [n]
  {:name          :skilled
   :options       skill/all ;; minus already proficient
   :total-choices n
   ;; TODO: handle edge case where `n` > remaining skills
   :applicable    (fn [player chosen-skills]
                    {:pre [(= (count chosen-skills) n)]}
                    (reduce #(add-proficiency %1 :skills %2)
                            player
                            chosen-skills))})

(def skilled
  {:name :skilled
   :apply #(update %1 :choosable-traits conj (trait/skilled 3))})

(def trait/weapon-master
  {:name :weapon-master
   :options weapon/all  ;; minus already proficient
   :total-choices 4
   :applicable (fn [player chosen-weapons]
                 (reduce #(add-proficiency %1 :weapons %2)
                         player
                         chosen-weapons))})

(def weapon-master
  {:name :weapon-master
   :apply #(update %1 :choosable-traits conj trait/weapon-master)})

(def all [heavily-armored
          lightly-armored
          moderately-armored
          resilient
          skilled])
