(ns dnd.armor.medium
  (:require [dnd.armor.category :refer [medium]]
            [dnd.player :as player]
            [dnd.util :as util]))

(defn armor-class [base pc] (+ base (min 2 (player/dex-modifier pc))))

(def hide
  {:name :hide
   :category medium
   :cost {:gold 10}
   :armor-class (partial armor-class 12)
   :weight 12})

(def chain-shirt
  {:name :chain-shirt
   :category medium
   :cost {:gold 50}
   :armor-class (partial armor-class 13)
   :weight 20})

(def scale-mail
  {:name :scale-mail
   :category medium
   :cost {:gold 50}
   :armor-class (partial armor-class 14)
   :stealth util/disadvantage
   :weight 45})

(def breastplate
  {:name :breastplate
   :category medium
   :cost {:gold 400}
   :armor-class (partial armor-class 14)
   :weight 20})

(def half-plate
  {:name :half-plate
   :category medium
   :cost {:gold 750}
   :armor-class (partial armor-class 15)
   :stealth util/disadvantage
   :weight 40})

(def all #{hide chain-shirt scale-mail breastplate half-plate})
