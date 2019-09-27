(ns dnd.armor.util)

(defmacro defarmor [a-name category ac-update-fn armor]
  `(def ~a-name
     (-> ~armor
         (assoc :name (keyword (name '~a-name))
                :category ~category)
         (update :armor-class ~ac-update-fn))))
