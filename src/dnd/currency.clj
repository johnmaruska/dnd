(ns dnd.currency
  (:refer-clojure :rename {+ core-add
                           - core-sub})
  (:require [dnd.util :as util]))

(defn +
  ([] {:gold 0 :silver 0 :copper 0})
  ([& terms]
   (->> terms
        (map util/->copper)
        (reduce core-add)
        util/->minimal-coinage)))

(defn -
  [& terms]
  (->> terms
       (map util/->copper)
       (reduce core-sub)
       util/->minimal-coinage))

(defn can-afford? [player-currency cost]
  (boolean (>= (util/->copper player-currency)
               (util/->copper cost))))
