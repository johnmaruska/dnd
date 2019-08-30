(ns dnd.trait
  (:require [clojure.set :refer [union]]
            [dnd.armor :as armor]
            [dnd.stat :refer [increase-ability-score]]
            [dnd.weapon :as weapon]))

;; TODO: Brave (Halfling, PHB p28)
;; TODO: Cantrip (Elf, PHB p24)
;; TODO: Darkvision (Elf, PHB p23), (Dwarf, PHB p20)
;; TODO: Drow Magic (Elf, PHB p24)
;; TODO: Dwarven Resilience (Dwarf, PHB p20)
;; TODO: Dwarven Toughness (Dwarf, PHB p20)
;; TODO: Extra Language (Elf, Phb 24)
;; TODO: Fey Ancestry (Elf, PHB p23)
;; TODO: Halfling Nimbleness (halfling, PHB p28)
;; TODO: Lucky (Halfling, PHB p28)
;; TODO: Mask of the Wild (Elf, PHB p24)
;; TODO: Stonecunning (Dwarf, PHB p20)
;; TODO: Sunlight Sensitivity (Elf, PHB p24)
;; TODO: Superior Darkvision (Elf, PHB p24)
;; TODO: Tool Proficiency (Dwarf, PHB p20)
;; TODO: Trance (Elf, PHB p23)
;; TODO: Naturally stealthy

(defn ability-score-increase [ability amount]
  {:ability-score-increase #(increase-ability-score % ability amount)})

(def drow-weapon-training
  (let [add-proficiencies (partial union #{weapon/rapier
                                           weapon/shortsword
                                           weapon/hand-crossbow})]
    {:drow-weapon-training #(update % :proficiencies add-proficiencies)}))

(def dwarven-combat-training
  (let [add-proficiencies (partial union #{weapon/battleaxe
                                           weapon/handaxe
                                           weapon/throwing-hammer
                                           weapon/warhammer})]
    {:dwarven-combat-training #(update % :proficiencies add-proficiencies)}))

(def dwarven-armor-training
  (let [add-proficiencies (partial union #{armor/light armor/medium})]
    {:dwarven-armor-training #(update % :proficiencies add-proficiencies)}))

(def elf-weapon-training
  (let [add-proficiencies (partial union #{weapon/longsword
                                           weapon/shortsword
                                           weapon/shortbow
                                           weapon/longbow})]
    {:elf-weapon-training #(update % :proficiencies add-proficiencies)}))

(def keen-senses
  {:keen-senses #(update % :proficiencies
                         (partial union #{skill/perception}))})
