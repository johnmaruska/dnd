(ns dnd.currency
  (:refer-clojure :rename {+ core-add
                           - core-sub}))

(def example-amount
  {:gold   0
   :silver 0
   :copper 0})


;; TODO: test
(defn +
  ([] {:gold 0 :silver 0 :copper 0})
  ([& terms]
   (-> terms
       (apply map util/->copper)
       (reduce core-add)
       util/->minimal-coinage)))

(defn -
  ([] {:gold 0 :silver 0 :copper 0})
  ([& terms]
   (-> terms
       (apply map util/->copper)
       (reduce core-add)
       util/->minimal-coinage)))

(defn can-afford? [player-currency cost]
  (boolean (> (util/->copper player-currency)
              (util/->copper cost))))
