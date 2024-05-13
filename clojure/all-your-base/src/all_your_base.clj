(ns all-your-base)

(defn to-integer
  "Convert `digits` using base `base` into an integer."
  [coll base]
  (->> (iterate (partial * base) 1)
       (map * (reverse coll))
       (apply +)))

(defn from-integer
  "Convert an integer `n` to base `base` using positional notation."
  [n base]
  (->> (iterate #(quot % base) n)
       (take-while pos?)
       reverse
       (map #(mod % base))))

(defn valid-digits?
  "Check if the `digits` are valid given the `base`. E.g., for binary the only
  valid digits are 0 and 1."
  [digits base]
  (let [valid-digits (take base (iterate inc 0))

        digit-checks (map (fn [digit]
                            (some #(= % digit) valid-digits))
                          digits)]
    (not (some nil? digit-checks))))

(defn convert
  "Convert digits in digits-from (using base-from) to another list of digits
  using base-to.

  First we need to convert the digits into an integer primitive type which is
  base-agnostic. Then we can convert the integer to the target base using
  positional notation."
  [base-from digits-from base-to]
  (cond
    (some #(= base-from %) [-1 0 1])                nil
    (some #(= base-to   %) [-1 0 1])                nil
    (some #(= -1 %) digits-from)                    nil
    (not (valid-digits? digits-from base-from))     nil
    (< base-from 0)                                 nil
    (< base-to 0)                                   nil
    (empty? digits-from)                            []
    :else (let [converted (from-integer
                           (to-integer digits-from base-from)
                           base-to)]
            (if (empty? converted)
              '(0)
              converted))))
