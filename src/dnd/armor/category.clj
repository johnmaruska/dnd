(ns dnd.armor.category
  (:require [dnd.util :refer [minutes actions]]))

(def light
  {:name :light
   :don  {:q 1 :u minutes}
   :doff {:q 1 :u minutes}})

(def medium
  {:name :medium
   :don  {:q 5 :u minutes}
   :doff {:q 1 :u minutes}})

(def heavy
  {:name :heavy
   :don  {:q 10 :u minutes}
   :doff {:q 5  :u minutes}})

(def shields
  {:name :shields
   :don  {:q 1 :u actions}
   :doff {:q 1 :u actions}})

(defn light?  [armor] (= light   (armor :category)))
(defn medium? [armor] (= medium  (armor :category)))
(defn heavy?  [armor] (= heavy   (armor :category)))
(defn shield? [armor] (= shields (armor :category)))
