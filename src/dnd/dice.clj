(ns dnd.dice)

(def d4 :d4)
(def d6 :d6)
(def d8 :d8)
(def d10 :d10)
(def d12 :d12)
(def d20 :d20)
(def d100 :d100)

(defn- roll-d [n]
  (+ 1 (rand-int n)))

(defn roll
  ([die]
   (case die
     d100 (roll-d 100)
     d20  (roll-d 20)
     d12  (roll-d 12)
     d10  (roll-d 10)
     d8   (roll-d 8)
     d6   (roll-d 6)
     d4   (roll-d 4)))
  ([n die]
   (repeatedly n (roll die))))


(comment
  ;; roll 3d10!
  (roll 3 d10))
