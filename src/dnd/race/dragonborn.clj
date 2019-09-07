(ns dnd.race.dragonborn
  (:require [clojure.set :refer [union]]
            [dnd.alignment :as alignment]
            [dnd.language :as language]
            [dnd.trait :as trait]
            [dnd.stat :as stat]))

(def damage-type
  {:black  :acid
   :blue   :lightning
   :brass  :fire
   :bronze :lightning
   :copper :acid
   :gold   :fire
   :green  :poison
   :red    :fire
   :silver :cold
   :white  :cold})

;; TODO: move to traits/features
(comment
  (defn dragon-breath-weapon [dragon-flight]
    (let [line {:type :line :width 5 :range 30}
          cone {:type :cone :radius 15}]
      (assoc
       (case dragon-flight
         [:black :blue :bronze :brass :copper] {:save stat/DEX :area line}
         [:gold :red]                          {:save stat/DEX :area cone}
         [:green :silver :white]               {:save stat/CON :area cone})
       :damage-type (get damage-type dragon-flight)))))

(defn- damage-resistance
  "Find the name of the trait for damage resistance associated with given
  `dragon-flight`"
  [dragon-flight]
  (let [damage-type (get damage-type dragon-flight)]
    (keyword (str (name damage-type) "-resistance"))))

;; TODO: add/implement breath weapon feature
(defn- type->subrace [dragon-type]
  (keyword (str (name dragon-type) "-dragon")))

(defn apply-draconic-ancestry [player dragon-type]
  (let [subrace (type->subrace dragon-type)]
    (-> player
        (update :feature-traits union #{(damage-resistance dragon-flight)
                                        ;; todo, use dragonflight-specific
                                        :dragon-breath}))))

(def draconic-ancestry
  {:name :draconic-ancestry
   :options [:black :blue :bronze :brass :copper
             :gold :red :green :silver :white]
   :applicable apply-draconic-ancestry})

(def traits
  {:race :dragonborn
   :age {:maturity 15 :lifespan 80}
   :alignment [alignment/good alignment/evil]
   :applicable-traits [(trait/ability-score-increase stat/STR 2)
                       (trait/ability-score-increase stat/CHA 1)]
   :base-speed 30
   :choosable-traits [draconic-ancestry]
   ;; breath weapon depends on draconic ancestry, same damage-resistance
   :feature-traits #{:breath-weapon :damage-resistance}
   :height {:base {:feet 5 :inches 6} :modifier {:d8 2}}
   :languages #{language/common language/draconic}
   :weight {:base 175 :modifier {:d6 2}}})
