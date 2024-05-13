(ns all-your-base)

(defn pow [a b] (reduce * (repeat b a)))

(defn powers-of [base]
  (map #(pow base %) (iterate inc 0)))

(defn to-integer
  "Convert `digits` using base `base` into an integer."
  [digits-from base]
  (let [powers  (powers-of base)
        terms   (map vector (reverse digits-from) powers)
        addends (map (fn [[coeff power]] (* coeff power)) terms)
        sum     (reduce + addends)]
    sum))

(defn get-max-coeff
  "Given a number `n` and a `multiple`, check how many times `multiple` can fit
  into `n`."
  [n multiple]
  (let [coeffs (take-while #(<= (* % multiple) n)
                           (iterate inc 1))]
    (or (last coeffs) 0)))

(defn from-integer
  "Convert an integer `n` to base `base` using positional notation."
  [n base]
  (let [powers         (powers-of base)
        powers-lo-hi   (take-while #(>= n %) powers)
        powers-hi-lo   (reverse powers-lo-hi)
        coeffs         (second
                        (reduce (fn [[remaining digits] power]
                                  (let [max-coeff (get-max-coeff remaining power)]
                                    [(- remaining (* max-coeff power))
                                     (conj digits max-coeff)]))
                                [n []]
                                powers-hi-lo))]
    coeffs))

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
