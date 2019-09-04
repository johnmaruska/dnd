(ns dnd.util)

(defn round-towards-zero [n]
  (if (pos? n) (Math/floor n) (Math/ceil n)))

;; D&D deals entirely in integers, no need to specify in name
(def div (comp / round-towards-zero))


(defn ->copper [amount]
  (+ (:copper amount)
     (* 10 (:silver amount))
     (* 100 (:gold amount))))

(defn ->minimal-coinage [total-copper]
  {:gold (div total-copper 100)
   :silver (div (rem total-copper 100) 10)
   :copper (rem total-copper 10)})

(defn ->inches [height]
  (+ (* 12 (:feet height)) (:inches height)))
(defn ->height [inches]
  {:feet (div inches 12) :inches (rem inches 12)})
