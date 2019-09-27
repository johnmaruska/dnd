(ns dnd.bruenor
  (:require [clojure.string :as str]
            [dnd.player :as player]
            [dnd.stat :as stat]
            [dnd.trait :as trait]
            [dnd.race :as race]
            [dnd.race.dwarf :as dwarf]))

;;; step 1, Choose a race
(def race :todo)

;; TODO: implement racial characteristics

;;; Step 2: Choose a class
(def class :todo)

;;; Step 3: Determine ability scores
(def stat-priority [stat/STR stat/CON stat/WIS stat/CHA stat/DEX stat/INT])

(defn apply-racial-traits [player traits]
  (if (not (:subrace player))
    (-> player
        (merge (select-keys traits
                            [:race :subrace :size :base-speed :languages
                             :feature-traits :choosable-traits]))
        (trait/apply-all (:applicable-traits traits)))
    player))

(def bruenor
  (-> player/blank
      (stat/apply-standard-scores stat-priority)
      (race/with-race race/dark-elf)))

(clojure.pprint/pprint bruenor)
