(ns dnd.util)

(defn round-towards-zero [n]
  (if (pos? n) (Math/floor n) (Math/ceil n)))

;; D&D deals entirely in integers, no need to specify in name
(def div (comp / round-towards-zero))
