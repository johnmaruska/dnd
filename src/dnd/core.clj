(ns dnd.core
  (:require
   [clojure.set :refer [difference]]
   [clojure.pprint :refer [pprint]]
   [dnd.player :as player]
   [dnd.race :as race]
   [dnd.stat :as stat]
   [dnd.util.io :as io])
  (:gen-class))

(defn prompt-for-race []
  (io/prompt-user "Select one of the following races for your race:"
                  (vec race/all)
                  "Which race would your like your character to be?"))

(defn prompt-for-next-stat [current-priority-order remaining-options]
  (io/prompt-user :alphabetical
                  (str "Your current priority order from highest to lowest is "
                       current-priority-order
                       "\n\nRemaining ability scores are:")
                  (vec remaining-options)
                  "What stat is next?"))

(defn prompt-for-stat-priority []
  (println "Time to pick ability scores! What priority order - highest first, would you like?")
  (loop [curr-prio []
         rem-stats (set stat/all)]
    (if (= rem-stats #{})
      curr-prio
      (let [next-stat (prompt-for-next-stat curr-prio rem-stats)]
        (recur (conj curr-prio next-stat)
               (difference rem-stats #{next-stat}))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (println "Welcome to D&D!")
  (let [stat-prio   (prompt-for-stat-priority)
        chosen-race (prompt-for-race)]
    (pprint
     (-> player/blank-slate
         (stat/with-standard-scores stat-prio)
         (race/with-race chosen-race)))))
