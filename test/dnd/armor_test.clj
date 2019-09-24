(ns dnd.armor-test
  (:require [dnd.armor :as sut]
            [dnd.armor.heavy :as heavy]
            [dnd.armor.light :as light]
            [dnd.player :as player]
            [clojure.test :as t]))

(deftest stealth-disadvantage?
  (testing "true for known stealth disadvantaged armor (heavy)"
    (is (true? (sut/stealth-disadvantage? heavy/plate))))
  (testing "false for known stealth-neutral armor (light)"
    (is (false? (sut/stealth-disadvantage? light/padded)))))

(deftest ac-from-shield?
  (testing "no bonus when no shield equipped"
    ;; TODO: use `unequip :shield`
    )
  (testing "bonus equal to shield ac bonus when equipped"
    ;; TODO: use `equip armor/shield`
    ))

(deftest armor-class
  (testing ""))
