(ns dnd.util.io
  (:refer-clojure :rename {read-line core-read-line}))

(defn- read-line [] (flush) (core-read-line))

(defn- display-choices
  ([style choices]
   (let [display-choice (if (= style :alphabetical)
                          (fn [idx itm] (format "%s. %s" (-> (+ 1 64 idx) char str) itm))
                          (fn [idx itm] (format "%s. %s" (+ 1 idx) itm)))]
     (apply str (interpose "\n" (map-indexed display-choice choices)))))
  ([choices]
   (display-choices :numeral choices)))

(defn- retry-choice-thunk
  [thunk]
  (loop []
    (if-let [result (try (thunk)
                         (catch NumberFormatException e
                           (println "Please enter a valid choice: "))
                         (catch IllegalArgumentException e
                           (println "Please enter a valid choice: "))
                         (catch NullPointerException e
                           (println "Please enter a valid choice: ")))]
      result
      (recur))))

(defmacro with-bad-choice-reattempts [& body]
  `(retry-choice-thunk (fn [] ~@body)))

(defn- read-numeral-choice
  "Read a single numerical choice from the user."
  [total-choices]
  (with-bad-choice-reattempts
    (let [choice (Integer/parseInt (read-line))]
      (if (not (< 0 choice (+ 1 total-choices)))
        (throw (IllegalArgumentException. "invalid choice input"))
        choice))))

(defn- read-alphabetical-choice
  "Read a single alphabetical choice from the user."
  [total-choices]
  (with-bad-choice-reattempts
    (let [choice (-> (read-line) clojure.string/lower-case first int (- 96))]
      (if (not (< 0 choice (+ 1 total-choices)))
        (throw (IllegalArgumentException. "invalid choice input"))
        choice))))

(defn- read-choice
  ([style choices]
   (with-bad-choice-reattempts
     (if (= style :alphabetical)
       (read-alphabetical-choice (count choices))
       (read-numeral-choice (count choices)))))
  ([choices]
   (read-choice :numeral choices)))


(defn prompt-user
  "Prompt the user for their selection from a collection of choices.

  Accepts a preamble, a sequence of options to
  display, and a post-amble to lead the user's selection. Returns the item
  selected by the user.

  `preamble` and `postamble` must both be strings
  `choices` must be an indexable sequence (`nth` function)
  optional `style` may be :numeral or :alphabetical"
  ([style preamble choices postamble]
   (println preamble)
   (print (display-choices style choices))
   (print (format "\n\n%s: " postamble))
   (let [selected-number (read-choice style choices)]
     (nth choices (- selected-number 1))))
  ([preamble choices postamble]
   (prompt-user :numeral preamble choices postamble)))
