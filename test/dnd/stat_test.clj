(ns dnd.stat-test
  (:require [dnd.stat :as sut]
            [clojure.test :as t :refer [deftest testing is]]))

(deftest increase-ability-score
  (let [player {:ability-scores {sut/CHA 5}}]
    (testing "maxes at `sut/max-value` when exceeded"
      (is (= sut/max-value
             (-> player
                 (sut/increase-ability-score sut/CHA (* 2 sut/max-value))
                 (get-in [:ability-scores sut/CHA])))))
    (testing "overrides `sut/max-value` with optional keyword arg"
      (let [new-max 20]
        (is (= new-max
               (-> player
                   (sut/increase-ability-score sut/CHA (* 2 new-max) :max new-max)
                   (get-in [:ability-scores sut/CHA]))))))
    (testing "does not fully redef `sut/max-value`, retains previous value"
      (let [old-max sut/max-value]
        (sut/increase-ability-score player sut/CHA 123 :max 5)
        (is (= old-max sut/max-value))))))
