(ns dnd.language
  (:require [clojure.set :as set]))


(def abyssal :abyssal)
(def celestial :celestial)
(def common :common)
(def deep-speech :deep-speech)
(def draconic :draconic)
(def dwarvish :dwarvish)
(def elvish :elvish)
(def giant :giant)
(def gnomish :gnomish)
(def goblin :goblin)
(def halfling :halfling)
(def infernal :infernal)
(def orc :orc)
(def primordial :primordial)
(def sylvan :sylvan)
(def undercommon :undercommon)

(def standard #{common dwarvish elvish giant gnomish goblin halfling orc})
(def exotic #{abyssal celestial draconic deep-speech infernal primordial sylvan undercommon})
(def all (set/union standard exotic))
