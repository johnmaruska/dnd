(ns dnd.armor-test
  (:require [dnd.armor :as sut]
            [dnd.armor.heavy :as heavy]
            [dnd.armor.light :as light]
            [dnd.player :as player]
            [dnd.stat :as stat]
            [clojure.test :as t :refer [deftest testing is]]))

(deftest stealth-disadvantage?
  (testing "true for known stealth disadvantaged armor (heavy)"
    (is (true? (sut/stealth-disadvantage? heavy/plate))))
  (testing "false for known stealth-neutral armor (light)"
    (is (false? (sut/stealth-disadvantage? light/leather)))))

(deftest ac-from-shield?
  (testing "no bonus when no shield equipped"
    ;; TODO: use `unequip :shield`
    )
  (testing "bonus equal to shield ac bonus when equipped"
    ;; TODO: use `equip armor/shield`
    ))

(deftest armor-class
  (testing "player with no specifics defaults to base-armor"
    (let [test-player (stat/with-random-scores player/blank)]
      (is (= (sut/base-armor-class test-player)
             (sut/armor-class test-player))))))
