(ns dnd.dice)

(def d4 :d4)
(def d6 :d6)
(def d8 :d8)
(def d10 :d10)
(def d12 :d12)
(def d20 :d20)
(def d100 :d100)

(defn- roll-d [faces]
  (+ 1 (rand-int faces)))

(defn die->tot-faces [die]
  (-> (name die)
      (clojure.string/replace-first #"d" "")
      (Integer/parseInt)))

(defn roll
  ([die]
   (roll-d (die->tot-faces die)))
  ([n die]
   (repeatedly n #(roll die))))


(comment
  ;; roll 3d10!
  (roll d10)
  (roll 3 d10))
