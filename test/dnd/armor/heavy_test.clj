(ns dnd.armor.heavy-test
  (:require [dnd.armor.category :as category]
            [dnd.armor.heavy :as sut]
            [dnd.util :refer [disadvantage]]
            [clojure.test :as t :refer [deftest testing is]]))

(deftest defheavy
  (testing "adds name"
    (is (= :ring-mail (:name sut/ring-mail))))
  (testing "adds heavy category"
    (is (= category/heavy (:category sut/ring-mail))))
  (testing "adds `constantly` ac-update-fn"
    (is (= 14 ((:armor-class sut/ring-mail) {:fake "player"}))))
  (testing "adds stealth disadvantage"
    (is (= disadvantage (:stealth sut/ring-mail)))))
