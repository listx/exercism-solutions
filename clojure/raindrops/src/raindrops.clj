(ns raindrops
  (:require [clojure.string :as str]))

(defn divisible-by?
  "Return true if n can be divided evenly (no remainder) by the divisor."
  [divisor n]
  (= 0 (rem n divisor)))

(defmacro blurter
  "Return a string `s` if the given number is divisible by `divisor`. Otherwise
  return an empty string."
  ([divisor s]
   `#(if (divisible-by? ~divisor %) ~s "")))

(defn convert [n]
  (let [plinger (blurter 3 "Pling")
        planger (blurter 5 "Plang")
        plonger (blurter 7 "Plong")
        substrs (str/join ((juxt plinger planger plonger) n))]
    (if (str/blank? substrs)
      (str n)
      substrs)))
