(ns dnd.trait.applicable
  (:require [clojure.set :refer [union]]
            [dnd.armor.category :as armor.category]
            [dnd.skill :as skill]
            [dnd.stat :as stat]
            [dnd.weapon :as weapon]))

(defmacro deftrait
  "Define a choice trait. `body` must be a trait."
  [name body]
  `(def ~name (as-trait ~body)))

(defn as-trait [trait]
  (assoc trait :type :static))

(defn ability-score-increase
  [ability amount & {:keys [max] :or {max stat/max-value}}]
  (as-trait
   {:name  :ability-score-increase
    :apply #(stat/increase-ability-score % ability amount :max max)}))

(deftrait drow-weapon-training
  (let [proficiencies #{(weapon/get :rapier)
                        (weapon/get :shortsword)
                        (weapon/get :hand-crossbow)}]
    {:name  :drow-weapon-training
     :apply #(update-in % [:proficiencies :weapons] union proficiencies)}))

(deftrait dwarven-armor-training
  (let [proficiencies #{armor.category/light armor.category/medium}]
    {:name  :dwarven-armor-training
     :apply #(update-in % [:proficiencies :armor] union proficiencies)}))

(deftrait dwarven-combat-training
  (let [proficiencies #{(weapon/get :battleaxe)
                        (weapon/get :handaxe)
                        (weapon/get :throwing-hammer)
                        (weapon/get :warhammer)}]
    {:name  :dwarven-combat-training
     :apply #(update-in % [:proficiencies :weapons] union proficiencies)}))

(deftrait elf-weapon-training
  (let [proficiencies #{(weapon/get :longsword)
                        (weapon/get :shortsword)
                        (weapon/get :shortbow)
                        (weapon/get :longbow)}]
    {:name  :elf-weapon-training
     :apply #(update-in % [:proficiencies :weapons] union proficiencies)}))

(deftrait fleet-of-foot
  {:name  :fleet-of-foot
   :apply #(update % :base-speed (partial max 35))})

(deftrait keen-senses
  {:name  :keen-senses
   :apply #(update-in % [:proficiencies :skills] union #{skill/perception})})

(deftrait menacing
  {:name  :menacing
   :apply #(update-in % [:proficiencies :skills] union #{skill/intimidation})})

(deftrait tinker
  ;; TODO: tinker has way more stuff, PHB p37
  {:name  :tinker
   :apply #(update-in % [:proficiencies :skills] union #{:tinkers-tools})})


;; all that don't require
(def all #{drow-weapon-training
           dwarven-combat-training
           dwarven-armor-training
           elf-weapon-training
           fleet-of-foot
           keen-senses
           menacing
           tinker})
