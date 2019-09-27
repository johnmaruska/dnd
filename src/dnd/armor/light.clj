(ns dnd.armor.light
  (:require [dnd.armor.category :refer [light]]
            [dnd.player :as player]
            [dnd.util :as util]))

(defn armor-class [base pc] (+ base (player/dex-modifier pc)))

(defmacro deflight [name armor]
  `(def ~name
     (-> ~armor
         (assoc :name (keyword ~name)
                :category light)
         (update :armor-class #(partial armor-class %1)))))

(deflight padded
  {:cost        {:gold 5}
   :armor-class 11
   :stealth     util/disadvantage
   :weight      8})

(deflight leather
  {:cost        {:gold 10}
   :armor-class 11
   :weight      10})

(deflight studded-leather
  {:cost        {:gold 45}
   :armor-class 12
   :weight      13})

(def all #{padded leather studded-leather})
