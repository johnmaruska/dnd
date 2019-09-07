(ns dnd.core
  (:require
   [clojure.set :refer [difference]]
   [clojure.pprint :as pprint]
   [dnd.player :as player]
   [dnd.race :as race]
   [dnd.stat :as stat]
   [dnd.util.io :as io])
  (:gen-class))

(defn prompt-for-race []
  (io/prompt-user "Select one of the following races for your race:"
                  (vec race/all)
                  "Which race would your like your character to be?"
                  :style :alphabetical
                  :type :single))

(defn prompt-for-stat-priority []
  (io/prompt-user "Time to pick ability scores! What priority order would you like?"
                  stat/all
                  "Enter desired priority order, highest to lowest stat, <SPACE> separated"
                  :style :alphabetical
                  :type :order))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (println "Welcome to D&D!")
  (let [stat-prio   (prompt-for-stat-priority)
        chosen-race (prompt-for-race)]
    (pprint/pprint
     (-> player/blank-slate
         (stat/with-standard-scores stat-prio)
         (race/with-race chosen-race)))))
