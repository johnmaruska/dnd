(ns dnd.util-test
  (:require [dnd.util :as sut]
            [clojure.test :as t :refer [deftest testing is]]))


(deftest round-towards-zero
  (testing "rounds positives down"
    (is (= 5 (sut/round-towards-zero 5.5))))
  (testing "rounds negatives up"
    (is (= -5 (sut/round-towards-zero -5.5)))))

(deftest div
  (testing "rounds down to integer"
    (is (= 2 (sut/div 13 5))))
  (testing "rounds negatives down"
    (is (= -3 (sut/div -13 5)))))

(deftest ->copper
  (testing "converts currency amount to total copper value"
    (is (= 111 (sut/->copper {:copper 1 :silver 1 :gold 1})))))

(deftest ->minimal-coinage
  (testing "converts total copper value to coinage amount"
    (is (= {:copper 1 :silver 1 :gold 1}
           (sut/->minimal-coinage 111)))))

(deftest ->inches
  (testing "converts height maps to inches total"
    (is (= 65 (sut/->inches {:feet 5 :inches 5})))))

(deftest ->height
  (testing "converts total inches to height map"
    (is (= {:feet 5 :inches 5} (sut/->height 65)))))
