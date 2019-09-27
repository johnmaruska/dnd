(ns dnd.armor.light
  (:require [dnd.armor.category :refer [light]]
            [dnd.armor.util :as util]
            [dnd.player :as player]
            [dnd.util :refer [disadvantage]]))

(defn armor-class [base pc] (+ base (player/dex-modifier pc)))

(defmacro deflight [a-name armor]
  `(util/defarmor ~a-name light #(partial armor-class %1) ~armor))

(deflight padded
  {:cost        {:gold 5}
   :armor-class 11
   :stealth     disadvantage
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
