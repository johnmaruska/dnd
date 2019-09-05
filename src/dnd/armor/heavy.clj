(ns dnd.armor.heavy
  (:require [dnd.armor.category :refer [heavy]]
            [dnd.util :refer [disadvantage]]))

(def ring-mail
  {:name :ring-mail
   :category heavy
   :cost {:gold 30}
   :armor-class (constantly 14)
   :stealth disadvantage
   :weight 40})

(def chain-mail
  {:name :chain-mail
   :category heavy
   :cost {:gold 75}
   :armor-class (constantly 16)
   :strength 13
   :stealth disadvantage
   :weight 55})

(def splint
  {:name :splint
   :category heavy
   :cost {:gold 200}
   :armor-class (constantly 17)
   :strength 15
   :stealth disadvantage
   :weight 60})

(def plate
  {:name :plate
   :category heavy
   :cost {:gold 1500}
   :armor-class (constantly 18)
   :strength 15
   :stealth disadvantage
   :weight 65})

(def all #{ring-mail chain-mail splint plate})
