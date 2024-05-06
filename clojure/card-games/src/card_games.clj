(ns card-games)

(defn rounds
  "Takes the current round number and returns 
   a `list` with that round and the _next two_."
  [n]
  (list n (+ 1 n) (+ 2 n)))

(defn concat-rounds 
  "Takes two lists and returns a single `list` 
   consisting of all the rounds in the first `list`, 
   followed by all the rounds in the second `list`"
  [l1 l2]
  (concat l1 l2))

(defn contains-round? 
  "Takes a list of rounds played and a round number.
   Returns `true` if the round is in the list, `false` if not."
  [l n]
  (boolean (some #{n} l)))

(defn card-average
  "Returns the average value of a hand"
  [hand]
  (let [sum (reduce + hand)
        avg (/ (float sum) (count hand))]
    avg))

(defn approx-average?
  "Returns `true` if average is equal to either one of:
  - Take the average of the _first_ and _last_ number in the hand.
  - Using the median (middle card) of the hand."
  [hand]
  (let [avg-first-last (card-average (list (first hand) (last hand)))
        median (nth hand (quot (count hand) 2))]
    (or (== (card-average hand) avg-first-last)
        (== (card-average hand) median))))

(defn average-even-odd?
  "Returns true if the average of the cards at even indexes 
   is the same as the average of the cards at odd indexes."
  [hand]
  (let [evens (take-nth 2 (drop 1 hand))
        odds  (take-nth 2 hand)
        even-avg (card-average evens)
        odd-avg  (card-average odds)]
    (= even-avg odd-avg)))

(defn maybe-double-last
  "If the last card is a Jack (11), doubles its value
   before returning the hand."
  [hand]
  (if (= 11 (last hand))
    (concat (butlast hand) '(22))
    hand))
