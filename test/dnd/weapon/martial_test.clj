(ns dnd.weapon.martial-test
  (:require [dnd.dice :refer [d8]]
            [dnd.weapon.util :as util]
            [dnd.weapon.martial :as sut]
            [clojure.test :refer [deftest testing is are]]))

(deftest defmelee
  (testing "adds name"
    (is (= :battleaxe (:name sut/battleaxe))))
  (testing "adds category"
    (is (= util/martial (:category sut/battleaxe))))
  (testing "adds type"
    (is (= util/melee (:type sut/battleaxe))))
  (testing "adds damage"
    (is (= {:dice {:d8 1} :type util/slashing}
           (:damage sut/battleaxe)))))
