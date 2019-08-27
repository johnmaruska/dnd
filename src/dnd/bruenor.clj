(ns dnd.bruenor
  (:require [clojure.string :as str]))

(def bruenor (atom {}))

;;; step 1, Choose a race
(def race :todo)

;; TODO: implement racial characteristics

;;; Step 2: Choose a class
(def class :todo)

;;; Step 3: Determine ability scores
(def stat-priority [stats/STR stat/CON stat/WIS stat/CHA stat/DEX str/INT])

(let [bruenor {}
      b-with-stats (player/apply-standard-scores bruenor)]
  b-with-stats)
