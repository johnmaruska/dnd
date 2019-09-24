(ns dnd.armor.heavy
  (:require [dnd.armor.category :refer [heavy]]
            [dnd.util :refer [disadvantage]]))

(defmacro defheavy [name armor]
  `(def ~name
     (-> ~armor
         (assoc :name ~name
                :category heavy
                :stealth disadvantage)
         (update :armor-class constantly))))

(defheavy ring-mail
  {:cost {:gold 30}
   :armor-class 14
   :weight 40})

(defheavy chain-mail
  {:cost {:gold 75}
   :armor-class 16
   :strength 13
   :weight 55})

(defheavy splint
  {:cost {:gold 200}
   :armor-class 17
   :strength 15
   :weight 60})

(defheavy plate
  {:cost {:gold 1500}
   :armor-class 18
   :strength 15
   :weight 65})

(def all #{ring-mail chain-mail splint plate})
