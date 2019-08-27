(ns dnd.dice)

(defn roll-d [n]
  (+ 1 (rand-int n)))

(defmulti roll identity)
(defmethod roll :d100 [] (roll-d 100))
(defmethod roll :d20 [] (roll-d 20))
(defmethod roll :d12 [] (roll-d 12))
(defmethod roll :d10 [] (roll-d 10))
(defmethod roll :d8 [] (roll-d 8))
(defmethod roll :d6 [] (roll-d 6))
(defmethod roll :d4 [] (roll-d 4))

(comment
  (take 3 (repeat (roll :d10))))
