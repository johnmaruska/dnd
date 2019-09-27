(ns dnd.race-test
  (:require [dnd.player :refer [blank]]
            [dnd.race :as sut]
            [dnd.stat :as stat]
            [clojure.test :as t :refer [deftest testing is]]))

(deftest with-race
  (let [stat-priority [stat/STR stat/CON stat/WIS stat/CHA stat/DEX stat/INT]
        player        (stat/with-standard-scores blank stat-priority)]
    (testing "with-race hilldwarf"
      (is (sut/with-race player sut/hill-dwarf)))
    (testing "with-race mountain dwarf"
      (is (sut/with-race player sut/mountain-dwarf)))
    (testing "with-race dark elf"
      (is (sut/with-race player sut/dark-elf)))
    (testing "with-race high elf"
      (is (sut/with-race player sut/high-elf)))
    (testing "with-race wood elf"
      (is (sut/with-race player sut/wood-elf)))
    (testing "with-race lightfoot halfling"
      (is (sut/with-race player sut/lightfoot-halfling)))
    (testing "with-race stout halfling"
      (is (sut/with-race player sut/stout-halfling)))
    (testing "with-race human"
      (is (sut/with-race player sut/human)))
    (testing "with-race human-with-feat"
      (is (sut/with-race player :human-with-feat)))
    (testing "with-race human-with-skills"
      (is (sut/with-race player :human-with-skills)))
    (testing "with-race human-with-ability"
      (is (sut/with-race player :human-with-ability)))
    (testing "with-race half-elf"
      (is (sut/with-race player sut/half-elf)))
    (testing "with-race half-orc"
      (is (sut/with-race player sut/half-orc)))
    (testing "with-race dragonborn"
      (is (sut/with-race player sut/dragonborn)))
    (testing "with-race forest gnome"
      (is (sut/with-race player sut/forest-gnome)))
    (testing "with-race rock gnome"
      (is (sut/with-race player sut/rock-gnome)))
    (testing "with-race tiefling"
      (is (sut/with-race player sut/tiefling)))
    ))
