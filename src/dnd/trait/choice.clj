(ns dnd.trait.choice
  (:require [dnd.language :as language]
            [dnd.player :as player]
            [dnd.stat :as stat]
            [dnd.trait.applicable :as applicable]))

(defmacro deftrait
  "Define a choice trait. `body` must be a trait."
  [name body]
  `(def ~name (as-trait ~body)))

(defn as-trait [trait]
  (assoc trait :type :choice))

(defn tool-proficiency [options]
  (as-trait
   {:name    :tool-proficiency
    :options (vec options)
    :apply   #(player/add-proficiency %1 :tools-and-kits %2)}))

(defn ability-score-increase
  ([options & {:keys [max]}]
   (as-trait
    {:name       :ability-score-increase
     :options    (vec options)
     :applicable #(applicable/ability-score-increase %1 %2 1 :max max)}))
  ([] (ability-score-increase stat/all)))

(deftrait extra-language
  {:name       :extra-language
   :options    language/all
   :applicable player/add-language})

(deftrait skill-versatility
  {:name       :skill-versatility
   :options    skill/all
   :applicable #(player/add-proficiency %1 :skill %2)})

(deftrait extra-feat
  {:name       :extra-feat
   :options    feat/all
   :applicable player/add-feat})

(def all #{extra-feat skill-versatility extra-language})
