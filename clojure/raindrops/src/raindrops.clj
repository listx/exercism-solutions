(ns raindrops
  (:require [clojure.string :as str]))

(defn divisible-by?
  "Return true if n can be divided evenly (no remainder) by the divisor."
  [divisor n]
  (= 0 (rem n divisor)))

(defn convert [n]
  (let [plinger #(if (divisible-by? 3 %) "Pling" "")
        planger #(if (divisible-by? 5 %) "Plang" "")
        plonger #(if (divisible-by? 7 %) "Plong" "")
        substrs (str/join ((juxt plinger planger plonger) n))]
    (if (str/blank? substrs)
      (str n)
      substrs)))
