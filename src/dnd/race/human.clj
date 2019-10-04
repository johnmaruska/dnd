(ns dnd.race.human
  (:require
   [dnd.alignment :as alignment]
   [dnd.feat :as feat]
   [dnd.language :as language]
   [dnd.skill :as skill]
   [dnd.stat :as stat]
   [dnd.trait :as trait]
   [dnd.trait.choice :as choice-traits]))

(def traits
  {:race :human
   :age {:maturity 18 :lifespan 90}
   :alignments alignment/all
   :height {:base {:feet 4 :inches 8} :modifier {:d10 2}}
   :weight {:base 110 :modifier {:d4 2}}
   :size :medium
   :base-speed 30
   :languages #{language/common}
   :applicable-traits (map #(trait/ability-score-increase % 1) stat/all)
   :choosable-traits [choice-traits/extra-language]})

(def variant-traits-ability-score
  (-> traits
      (dissoc :applicable-traits)
      (update :choosable-traits concat [{:ability-score-increase stat/all}
                                        {:ability-score-increase stat/all}])))

(def variant-traits-skills
  (-> traits
      (dissoc :applicable-traits)
      (update :choosable-traits concat [{:skill-versatility skill/all}
                                        {:skill-versatility skill/all}])))

(def variant-trait-feat
  (-> traits
      (dissoc :applicable-traits)
      (update :choosable-traits conj {:extra-feat feat/all})))
