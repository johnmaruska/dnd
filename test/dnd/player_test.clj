(ns dnd.player-test
  (:require [dnd.player :as sut]
            [dnd.stat :as stat]
            [clojure.pprint :as pprint]
            [clojure.test :refer [deftest testing is are]]))

(deftest set-level
  ;; TODO: do I want to exception/handle instead?
  (testing "enforces maximum level 20"
    (is (= 20 (:level (sut/set-level {:level 10} 27)))))
  (testing "enforces minimum level 1"
    (is (= 1 (:level (sut/set-level {:level 10} -5))))))

(deftest level-up
  ;; TODO: do I want to exception/handle instead?
  (testing "enforces maximum level 20"
    (let [player {:level 20}]
      (is (= player (sut/level-up player))))))

(deftest add-proficiency
  (testing "adds a proficiency"
    (is (contains? (-> {:proficiencies {:skill #{}}}
                       (sut/add-proficiency :skill :fake-skill)
                       (get-in [:proficiencies :skill]))
                   :fake-skill))))

(deftest add-language
  (testing "adds a language"
    (is (contains? (-> {:languages #{}}
                       (sut/add-language :orcish)
                       :languages)
                   :orcish))))

(deftest add-feat
  (testing "adds a feat"
    (is (contains? (-> {:feats #{}}
                       (sut/add-feat :ex-feat)
                       :feats)
                   :ex-feat))))

;;; Player's Handbook page 13
(defn- modifier-table [score]
  (case score
    1       -5
    (2 3)   -4
    (4 5)   -3
    (6 7)   -2
    (8 9)   -1
    (10 11) 0
    (12 13) 1
    (14 15) 2
    (16 17) 3
    (18 19) 4
    (20 21) 5
    (22 23) 6
    (24 25) 7
    (26 27) 8
    (28 29) 9
    30      10))

(deftest get-ability-modifier
  (testing "calculated modifier matches table"
    (let [possible-scores (vec (map inc (range 30)))
          ability-fns     [sut/cha-modifier
                           sut/con-modifier
                           sut/dex-modifier
                           sut/int-modifier
                           sut/str-modifier
                           sut/wis-modifier]]
      (dorun (for [ability-fn ability-fns
                   score      possible-scores]
               (let [score-map {:ability-scores (->> stat/all
                                                     (map (fn [s] [s score]))
                                                     (into {}))}]
                 (is (= (modifier-table score) (ability-fn score-map)))))))))
