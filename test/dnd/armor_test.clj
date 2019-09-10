(ns dnd.armor-test
  (:require [dnd.armor :as armor]
            [dnd.armor.category :as category]
            [dnd.armor.light :as light]
            [dnd.armor.medium :as medium]
            [dnd.armor.heavy :as heavy]
            [dnd.stat :as stat]
            [clojure.test :as t :refer [deftest is testing]]))

;;;; armor top-level

(deftest stealth-disadvantage?
  (testing "disadvantage can be determined from armor"
    (is (true? (armor/stealth-disadvantage? heavy/ring-mail)))))

(deftest base-armor-class
  (testing "base armor class calculated properly"
    (let [player (-> {}
                     stat/with-random-scores
                     (stat/set-ability-score stat/DEX 14))]
      (= 12 (armor/base-armor-class player)))))

;; TODO: test ac-from-shield and armor-class, can only be tested after equipping
;; to a character is implemented

;;;; armor.category

(deftest light?
  (testing "light? works on all light armor"
    (is (every? category/light? light/all))))

(deftest medium?
  (testing "medium? works on all medium armor"
    (is (every? category/medium? medium/all))))

(deftest heavy?
  (testing "heavy? works on all heavy armor"
    (is (every? category/heavy? heavy/all))))

(deftest shield?
  (testing "shield? works on the shield item"
    (is (category/shield? armor/shield))))


(deftest light-armor-class
  (testing "light armor-class flat adds dex modifier"
    (let [base-ac 10
          player (-> (stat/with-random-scores {})
                     (stat/set-ability-score stat/DEX 18))]
      (= (+ base-ac 4) (light/armor-class base-ac player)))))

(deftest medium-armor-class
  (testing "medium armor-class caps dex modifier bonus to 2"
    (let [base-ac 10
          player (-> (stat/with-random-scores {})
                     (stat/set-ability-score stat/DEX 18))]
      (= (+ base-ac 2) (light/armor-class base-ac player)))))
