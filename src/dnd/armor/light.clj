(ns dnd.armor.light
  (:require [dnd.armor.category :refer [light]]
            [dnd.player :as player]
            [dnd.util :as util]))

(defn armor-class [base pc] (+ base (player/dex-modifier pc)))

(def padded
  {:name :padded
   :category light
   :cost {:gold 5}
   :armor-class (partial armor-class 11)
   :stealth util/disadvantage
   :weight 8})

(def leather
  {:name :leather
   :category light
   :cost {:gold 10}
   :armor-class (partial armor-class 11)
   :weight 10})

(def studded-leather
  {:name :studded-leather
   :category light
   :cost {:gold 45}
   :armor-class (partial armor-class 12)
   :weight 13})

(def all #{padded leather studded-leather})
