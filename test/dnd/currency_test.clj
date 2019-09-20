(ns dnd.currency-test
  (:refer-clojure :rename {+ core-add
                           - core-sub})
  (:require [dnd.currency :as sut]
            [clojure.test :as t :refer [deftest testing is]]))

(deftest +
  (testing "defaults to 0 coins with no args"
    (is (= {:gold 0 :silver 0 :copper 0} (sut/+))))
  (testing "adds several amounts together properly"
    (is (= {:gold 5 :silver 5 :copper 5}
           (sut/+ {:gold 1 :silver 2 :copper 3}
                  {:gold 2 :silver 3 :copper 4}
                  {:gold 1 :silver 9 :copper 8})))))

(deftest -
  (testing "subtracts several amounts"
    (is (= {:gold 2 :silver 8 :copper 3}
           (sut/- {:gold 5 :silver 5 :copper 5}
                  {:gold 1 :silver 4 :copper 1}
                  {:gold 1 :silver 3 :copper 1})))))

(deftest can-afford?
  (testing "true when first > second"
    (is (true? (sut/can-afford? {:gold 5} {:gold 3}))))
  (testing "true when first = second"
    (is (true? (sut/can-afford? {:gold 5} {:gold 5}))))
  (testing "false when first < second"
    (is (false? (sut/can-afford? {:gold 3} {:gold 5})))))
