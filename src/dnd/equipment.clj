(ns dnd.equipment)


;; Slots: Sheild, Armor, Weapon


(defn equipped-item
  "return item equipped in slot if there is one"
  [player slot]
  (-> player :equipment slot))

(def equipped? (comp boolean equipped-item))

(def slots
  #{:armor :main-hand :off-hand :head})
