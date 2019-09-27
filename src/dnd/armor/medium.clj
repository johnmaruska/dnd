(ns dnd.armor.medium
  (:require [dnd.armor.category :refer medium]
            [dnd.player :as player]
            [dnd.util :as util]))

(defn armor-class [base pc]
  (+ base (min 2 (player/dex-modifier pc))))

(defmacro defmedium [name armor]
  `(def ~name
     (-> ~armor
         (assoc :name (keyword ~name)
                :category medium)
         (update :armor-class #(partial armor-class %1)))))

(defmedium hide
  {:cost        {:gold 10}
   :armor-class 12
   :weight      12})

(defmedium chain-shirt
  {:cost        {:gold 50}
   :armor-class 13
   :weight      20})

(defmedium scale-mail
  {:cost        {:gold 50}
   :armor-class 14
   :stealth     util/disadvantage
   :weight      45})

(defmedium breastplate
  {:cost        {:gold 400}
   :armor-class 14
   :weight      20})

(defmedium half-plate
  {:cost        {:gold 750}
   :armor-class 15
   :stealth     util/disadvantage
   :weight      40})

(def all #{hide chain-shirt scale-mail breastplate half-plate})
