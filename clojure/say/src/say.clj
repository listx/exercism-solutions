(ns say
  (:require [clojure.string :as str]))

(def ^:private ^:const leaf
  {0 "zero"
   1 "one"
   2 "two"
   3 "three"
   4 "four"
   5 "five"
   6 "six"
   7 "seven"
   8 "eight"
   9 "nine"
   10 "ten"
   11 "eleven"
   12 "twelve"
   13 "thirteen"
   14 "fourteen"
   15 "fifteen"
   16 "sixteen"
   17 "seventeen"
   18 "eighteen"
   19 "nineteen"
   20 "twenty"
   30 "thirty"
   40 "forty"
   50 "fifty"
   60 "sixty"
   70 "seventy"
   80 "eighty"
   90 "ninety"})

(def ^:private ^:const units
  [[1000000000 "billion"]
   [1000000    "million"]
   [1000       "thousand"]
   [100        "hundred"]])

(defn- to-prose
  "Convert an integer between 0 and 999999999999 (inclusive) into prose. Convert
  the number into a vec if necessary, where the last element of the vec is the
  remaining integer number that still needs to be converted. E.g., the
  'iterative' view of this function for the number 123555 will look something
  like:

    [\"one hundred twenty-three\" \"thousand\" 555]

    [\"one hundred twenty-three\" \"thousand\"
      (str/join \" \" [\"five hundred\" 55])]

    [\"one hundred twenty-three\" \"thousand\"
      (str/join \" \" [\"five hundred\"
        (str/join [\"fifty-five\" 0])])]

    [\"one hundred twenty-three\" \"thousand\"
      (str/join \" \" [\"five hundred\"
        (str/join [\"fifty-five\" nil])])]

  at which point the recursion terminates and the stack can be reduced like

    [\"one hundred twenty-three\" \"thousand\"
      (str/join \" \" [\"five hundred\" \"fifty-five\"])]

    [\"one hundred twenty-three\" \"thousand\" \"five hundred fifty-five\"]

  and so on back to just

    \"one hundred twenty-three thousand five hundred fifty-five\"

  with the final str/join call.

  This fn uses recursion to build up the stack."
  [n]
  (cond
        ;; A "leaf" number is a number that can no longer be broken down into
        ;; smaller sub-parts. It's a direct hashmap lookup.
        (contains? leaf n) (leaf n)
        ;; If the number is 21-99, then add a hyphen.
        (< n 100) (let [r (rem n 10)]
                      (str (to-prose (- n r)) "-" (to-prose r)))
        :else
          (let [
                ;; Find the largest unit (e.g., billion) that fits into the
                ;; number.
                [[unit unit-desc]] (drop-while (fn [[unit]] (< n unit)) units)
                ;; Get the "coefficient" of the largest unit that fits. If the
                ;; number is 55123 then `coeff` = "fifty-five" and `r` = 123.
                [coeff-str r]      ((juxt (comp to-prose quot) rem) n unit)
                ;; Expand any remaining coefficient.
                expanded           (when (pos? r) (to-prose r))]
            (->> [coeff-str unit-desc expanded]
                 (str/join " ")
                 str/trimr))))

(defn number [n]
  (if (not (<= 0 n 999999999999))
    (throw (IllegalArgumentException. "Number out of bounds."))
    (to-prose n)))
