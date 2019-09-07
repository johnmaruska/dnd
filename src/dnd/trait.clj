(ns dnd.trait
  (:require [clojure.set :refer [union]]
            [dnd.armor :as armor]
            [dnd.armor.category :as armor.category]
            [dnd.skill :as skill]
            [dnd.stat :refer [increase-ability-score]]
            [dnd.weapon :as weapon]))

(defn apply-single-trait [player trait]
  (let [trait-fn (nth (first trait) 1)]
    (trait-fn player)))

(defn apply-all [player traits]
  (reduce apply-single-trait player traits))

;;; Dwarf - PHB 18-20
;; TODO: Darkvision
;; TODO: Dwarven Resilience
;; TODO: Stonecunning
;; TODO: Dwarven Toughness

;; Elf - PHB 21-24
;; TODO: Darkvision
;; TODO: Mask of the Wild
;; TODO: Fey Ancestry
;; TODO: Trance
;; TODO: Superior Darkvision
;; TODO: Sunlight Sensitivity
;; TODO: Drow Magic
;; TODO: Cantrip

;;; Halfling - PHB 26-28
;; TODO: Halfling Nimbleness
;; TODO: Brave
;; TODO: Lucky
;; TODO: Naturally Stealthy
;; TODO: Stout Resilience

;;; Dragonborn - PHB 32-34
;; TODO: Breath Weapon (based on draconic ancestry)
;; TODO: Damage Resistance (based on draconic ancestry)

;; Gnome - PHB 35-37
;; TODO: Darkvision
;; TODO: Gnome Cunning
;; TODO: Artificer's Lore
;; TODO: Tinker
;; TODO: Natural Illusionist
;; TODO: Speak with Small Beasts

;;; Half-Orc - PHB 40-21
;; TODO: Relentless Endurance
;; TODO: Savage Attacks

;;; Tiefling - PHB 42-43
;; TODO: Hellish Resistance
;; TODO: Infernal Legacy

(defn ability-score-increase [ability amount & {:keys [max]}]
  {:ability-score-increase #(increase-ability-score % ability amount :max max)})

(def drow-weapon-training
  (let [proficiencies #{weapon/rapier
                        weapon/shortsword
                        weapon/hand-crossbow}]
    {:drow-weapon-training #(update % :proficiencies union proficiencies)}))

(def dwarven-combat-training
  (let [proficiencies #{weapon/battleaxe
                        weapon/handaxe
                        weapon/throwing-hammer
                        weapon/warhammer}]
    {:dwarven-combat-training #(update % :proficiencies union proficiencies)}))

(def dwarven-armor-training
  (let [proficiencies #{armor.category/light armor.category/medium}]
    {:dwarven-armor-training #(update % :proficiencies union proficiencies)}))

(def elf-weapon-training
  (let [proficiencies #{weapon/longsword
                        weapon/shortsword
                        weapon/shortbow
                        weapon/longbow}]
    {:elf-weapon-training #(update % :proficiencies union proficiencies)}))

(def fleet-of-foot
  {:fleet-of-foot #(update % :base-speed (partial max 35))})

(def keen-senses
  {:keen-senses #(update % :proficiencies union #{skill/perception})})

(def menacing
  {:menacing #(update % :proficiencies union #{skill/intimidation})})

(def tinker
  ;; TODO: tinker has way more stuff, PHB p37
  {:tinker #(update % :proficiencies union #{:tinkers-tools})})

;;;; Choosable Traits - TODO: move to own namespace

(defn add-proficiency [player type proficiency]
  (update-in player [:proficiencies type] union #{proficiency}))

(defn add-language [player proficiency]
  (update player :languages union #{proficiency}))

(defn tool-proficiency [options]
  {:name    :tool-proficiency
   ;; TODO: define tool-and-kit proficiencies
   :options (vec options)
   :apply   #(add-proficiency %1 :tools-and-kits %2)})

(defn choosable->applicable [choosable choice]
  {(:name choosable) #((:apply choosable) %1 choice)})

(def extra-language
  {:name       :extra-language
   :options    language/all
   :applicable #(update %1 :languages union #{%2})})

(defn ability-score-increase
  ([options & {:keys [max]}]
   {:name       :ability-score-increase
    :options    (vec options)
    :applicable #(applicable/ability-score-increase %1 %2 1 :max max)})
  ([] (ability-score-increase stat/all)))

(def skill-versatility
  {:name       :skill-versatility
   :options    skill/all
   :applicable #(add-proficiency %1 :skill %2)})

(def extra-feat
  {:name :extra-feat
   :options feat/all
   :applicable #(update-in %1 :feats union #{%2})})
