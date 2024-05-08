(ns raindrops
  (:require [clojure.string :as str]))

(defn divisible-by?
  "Return true if n can be divided evenly (no remainder) by the divisor."
  [divisor n]
  (zero? (mod n divisor)))

(defn convert [n]
  (cond-> nil
    (divisible-by? 3 n) (str "Pling")
    (divisible-by? 5 n) (str "Plang")
    (divisible-by? 7 n) (str "Plong")
    :else (or (str n))))
