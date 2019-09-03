(ns dnd.race.human
  (:require
   [dnd.alignment :as alignment]
   [dnd.feat :as feat]
   [dnd.language :as language]
   [dnd.skill :as skill]
   [dnd.stat :as stat]
   [dnd.trait :as trait]))

(def traits
  {:age {:maturity 18 :lifespan 90}
   :alignments alignment/all
   :height {:short 5 :tall 6}
   :size :medium
   :base-speed 30
   :languages #{language/common}
   :applicable-traits (map #(trait/ability-score-increase % 1) stat/all)
   :choosable-traits [{:extra-language language/all}]})

(def variant-traits-ability-score
  (-> traits
      (dissoc :applicable-traits)
      (update :choosable-traits
              (partial concat [{:ability-score-increase stat/all}
                               {:ability-score-increase stat/all}]))))

(def variant-traits-skills
  (-> traits
      (dissoc :applicable-traits)
      (update :choosable-traits
              (partial concat [{:proficiency skill/all}
                               {:proficiency skill/all}]))))

(def variant-trait-feat
  (-> traits
      (dissoc :applicable-traits)
      (update :choosable-traits
              (conj {:feat feat/all}))))
