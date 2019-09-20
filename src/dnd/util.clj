(ns dnd.util)

(defn round-towards-zero [n]
  (int (if (pos? n)
         (Math/floor n)
         (Math/ceil n))))

(defn round-down [n] (int (Math/floor n)))

;; D&D deals entirely in integers, no need to specify in name
(def div (comp round-down /))

;;;; Currency

(defn ->copper [{:keys [copper silver gold]
                 :or   {copper 0
                        silver 0
                        gold   0}}]
  (+ copper (* 10 silver) (* 100 gold)))

(defn ->minimal-coinage [total-copper]
  {:gold (div total-copper 100)
   :silver (div (rem total-copper 100) 10)
   :copper (rem total-copper 10)})

;;;; Height

(defn ->inches [height]
  (+ (* 12 (:feet height)) (:inches height)))
(defn ->height [inches]
  {:feet (div inches 12) :inches (rem inches 12)})

;;;; Time

(def minutes :minutes)
(def actions :actions)

;;;; Status signifiers

(def disadvantage :disadvantage)

;;;; Damage

(def flat {:amount 1 :type :bludgeoning})
(def rolled {:dice {:d4 1} :type :piercing})
