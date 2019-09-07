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

(defn- parse-numeral-choice [total-choices input]
  (let [choice (Integer/parseInt input)]
    (if (not (< 0 choice (+ 1 total-choices)))
      (throw (IllegalArgumentException. "invalid choice input"))
      choice)))

(defn- parse-alphabetical-choice [total-choices input]
  (let [choice (-> input clojure.string/lower-case first int (- 96))]
    (if (not (< 0 choice (+ 1 total-choices)))
      (throw (IllegalArgumentException. "invalid choice input"))
      choice)))

(defn- parse-single-choice
  [style choices input]
  (let [choice (if (= style :alphabetical)
                 (parse-alphabetical-choice (count choices) input)
                 (parse-numeral-choice (count choices) input))]
    (nth choices (- choice 1))))

(defn- parse-multiple-choices
  [style choices input]
  (let [parse-fn (if (= style :alphabetical)
                   (partial parse-alphabetical-choice (count choices))
                   (partial parse-numeral-choice (count choices)))]
    (map parse-fn (clojure.string/split input #" "))))

(defn- parse-order-choice
  [style choices input]
  (let [chosen-order (parse-multiple-choices style choices input)
        all-used? (= (count choices) (count (set chosen-order)))
        no-dupes? (= (count chosen-order) (count (set chosen-order)))]
    (if (and all-used? no-dupes?)
      chosen-order
      (throw (IllegalArgumentException. "must use all options once")))))

(defn- parse-n-subset-choice
  [n style choices input]
  (let [chosen-subset (parse-multiple-choices style choices input)
        n-choices? (= n (count chosen-subset))
        no-dupes? (= n (count (set chosen-subset)))]
    (if (and n-choices? no-dupes?)
      chosen-subset
      (throw (IllegalArgumentException. (str "must use " n " different choices"))))))

(defn retrieve-choice! [parse-fn style choices]
  (with-bad-choice-reattempts
    (parse-fn style choices (read-line))))

(defn get-choice!
  "Get user input of their choice of a given set of `choices`. The `choice-type`
  must be one of `single` (one item from the set) `order` (order all items in
  the set) or `subset` "
  ([choice-type style choices]
   (let [parse-fn (case choice-type
                    :single   parse-single-choice
                    :order    parse-order-choice
                    :multiple parse-multiple-choices)]
     (retrieve-choice! parse-fn style choices)))
  ([choice-type n style choices]
   {:pre [(= :subset choice-type)]}
   (retrieve-choice! (partial parse-n-subset-choice n) style choices)))

(defn prompt-user
  "Prompt the user for their selection from a collection of choices.

  Accepts a preamble, a sequence of options to
  display, and a post-amble to lead the user's selection. Returns the item
  selected by the user.

  `preamble` and `postamble` must both be strings
  `choices` must be an indexable sequence (`nth` function)
  optional `style` may be :numeral or :alphabetical"
  [preamble choices postamble & {:keys [style type]
                                 :or {style :numeral
                                      type :single}}]
  (println preamble)
  (print (display-choices style choices))
  (print (format "\n\n%s: " postamble))
  (get-choice! type style choices))
