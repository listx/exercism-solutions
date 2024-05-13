(ns all-your-base)

(defn to-integer
  "Convert `coll` digits using base `base` into an integer. The key is to set up a
  list of powers using the base (e.g., [1 2 4 8 16] for base 2), and then
  multiplying each one with the given digits in `coll`."
  [base coll]
  (->> (iterate (partial * base) 1)
       (map * (reverse coll))
       (apply +)))

(defn from-integer
  "Convert an integer `n` to base `base` using positional notation. The trick is
  to just divide repeatedly by the base, until we hit 0. Then we take the last
  digit of each one via the modulus."
  [base n]
  (->> (iterate #(quot % base) n)
       (take-while pos?)
       reverse
       (map #(mod % base))))

(defn convert
  "Convert digits in `coll` (using base-from) to another list of digits
  using base-to.

  First we need to convert the digits into an integer primitive type which is
  base-agnostic. Then we can convert the integer to the target base using
  positional notation."
  [base-from coll base-to]
  (cond
    (<= base-from 1) nil
    (<= base-to 1) nil
    ;; Numbers in `coll` must be between 0 and base, inclusive.
    (not-every? #(< -1 % base-from) coll) nil
    (empty? coll) []
    (every? zero? coll) [0]
    :else (->> coll
               (to-integer base-from)
               (from-integer base-to))))
