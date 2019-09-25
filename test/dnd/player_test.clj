(ns dnd.player-test
  (:require [dnd.player :as sut]
            [dnd.stat :as stat]
            [clojure.pprint :as pprint]
            [clojure.test :refer [deftest testing is are]]))

(def blank-player sut/blank)

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
    (let [player (sut/add-proficiency blank-player :skill :fake-skill)]
      (is (contains? (get-in player [:proficiencies :skill]) :fake-skill)))))

(deftest proficient?
  (testing "true when proficient"
    (is (true? (-> blank-player
                   (sut/add-proficiency :skill :fake-skill)
                   (sut/proficient?     :skill :fake-skill)))))
  (testing "false when not proficient"
    (is (false? (sut/proficient? blank-player :skill :fake-skill)))))

(deftest add-language
  (testing "adds a language"
    (let [player (sut/add-language blank-player :orcish)]
      (is (contains? (player :languages) :orcish)))))

(deftest add-feat
  (testing "adds a feat"
    (let [player (sut/add-feat blank-player :ex-feat)]
      (is (contains? (player :feats) :ex-feat)))))

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
               (let [score-map      (into {} (map (fn [s] [s score]) stat/all))
                     statted-player (-> blank-player
                                        (stat/with-custom-scores score-map))]
                 (is (= (modifier-table score) (ability-fn statted-player)))))))))

(defn- proficiency-table [level]
  (case level
    (1 2 3 4)     2
    (5 6 7 8)     3
    (9 10 11 12)  4
    (13 14 15 16) 5
    (17 18 19 20) 6))

(deftest proficiency-bonus
  (testing "calculated bonus matches table"
    (dorun
     (for [level (vec (map inc (range 20)))]
       (let [leveled-player (sut/set-level blank-player level)]
         (is (= (proficiency-table level)
                (sut/proficiency-bonus leveled-player))))))))
