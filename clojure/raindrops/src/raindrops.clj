(ns raindrops)

(defn divisible-by?
  "Return true if n can be divided evenly (no remainder) by the divisor."
  [divisor n]
  (= 0 (rem n divisor)))

(defn convert [n]
  (cond
    ((every-pred (partial divisible-by? 3)
                 (partial divisible-by? 5)
                 (partial divisible-by? 7)) n) "PlingPlangPlong"
    ((every-pred (partial divisible-by? 3)
                 (partial divisible-by? 5)) n) "PlingPlang"
    ((every-pred (partial divisible-by? 3)
                 (partial divisible-by? 7)) n) "PlingPlong"
    ((every-pred (partial divisible-by? 5)
                 (partial divisible-by? 7)) n) "PlangPlong"
    (divisible-by? 3 n) "Pling"
    (divisible-by? 5 n) "Plang"
    (divisible-by? 7 n) "Plong"
    :else (str n)))
