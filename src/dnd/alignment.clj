(ns dnd.alignment)

(def good :good)
(def neutral :neutral)
(def evil :evil)
(def lawful :lawful)
(def chaotic :chaotic)

(def all [good evil lawful chaotic neutral])

(def chaotic-evil    {:legality chaotic :morality evil})
(def chaotic-neutral {:legality chaotic :morality neutral})
(def chaotic-good    {:legality chaotic :morality good})
(def neutral-evil    {:legality neutral :morality evil})
(def true-neutral    {:legality neutral :morality neutral})
(def neutral-good    {:legality neutral :morality good})
(def lawful-evil     {:legality lawful  :morality evil})
(def lawful-neutral  {:legality lawful  :morality neutral})
(def lawful-good     {:legality lawful  :morality good})
