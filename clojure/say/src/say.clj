(ns say
  (:require [clojure.string :as str]))

(def low-words
  ["zero"
   "one"
   "two"
   "three"
   "four"
   "five"
   "six"
   "seven"
   "eight"
   "nine"
   "ten"
   "eleven"
   "twelve"
   "thirteen"
   "fourteen"
   "fifteen"
   "sixteen"
   "seventeen"
   "eighteen"
   "nineteen"])

(def billion  1000000000)
(def million  1000000)
(def thousand 1000)
(def hundred  100)

(def scalers
  {20 "twenty"
   30 "thirty"
   40 "forty"
   50 "fifty"
   60 "sixty"
   70 "seventy"
   80 "eighty"
   90 "ninety"
   hundred "hundred"
   thousand "thousand"
   million "million"
   billion "billion"})

(defn sub-units
  "Subtract units from the number. Given a vec of [num parts] (e.g., [34 [...]]),
  subtract the unit quantity from num (34 in the example) and append the amount
  subtracted to [...]."
  [show-coeff unit tuple]
  (let [[num parts] tuple
        coeff (quot num unit)
        remaining (- num (* coeff unit))]
    (if (pos? coeff)
      [remaining
       (if show-coeff
          (conj parts coeff (get scalers unit))
          (conj parts (get scalers unit)))]
      tuple)))

(defn lo-num
  "Convert a number between 19 and 1 to prose."
  [maybe-dash tuple]
  (let [[num parts] tuple
        word (get low-words num)]
    (if (pos? num)
      (conj parts (str maybe-dash word))
      parts)))

(defn med-num
  "Convert a number between 999 and 1 to prose."
  [tuple]
  (let [[num _] tuple
        maybe-dash (if (> num 20) "-" "")]
    (cond->> tuple
      (>= (first tuple) hundred)  (sub-units true hundred)
      (>= (first tuple) 90)       (sub-units false 90)
      (>= (first tuple) 80)       (sub-units false 80)
      (>= (first tuple) 70)       (sub-units false 70)
      (>= (first tuple) 60)       (sub-units false 60)
      (>= (first tuple) 50)       (sub-units false 50)
      (>= (first tuple) 40)       (sub-units false 40)
      (>= (first tuple) 30)       (sub-units false 30)
      (>= (first tuple) 20)       (sub-units false 20)
      (pos? (first tuple))        (lo-num maybe-dash))))

(defn drop-leading-zero
  [ps]
  (let [[leading & rest] ps]
    (if (and (int? leading)
             (zero? leading))
      rest
      ps)))

; Given an integer `num`, create a tuple of [num parts] where parts is a list of
; integers and strings, where the integers need to be converted to strings. We
; keep getting rid of integers in `parts` until there are no more. At this point
; we concatenate the string together. The `num` is repeatedly subtracted until
; we reach 0; at each step of the subtraction, we append the integer/string
; representation of the subtracted amount to `parts`.
(defn hi-num [num]
  (let [tuple [num []]]
    (cond->> tuple
      (>= (first tuple) billion)  (sub-units true billion)
      (>= (first tuple) million)  (sub-units true million)
      (>= (first tuple) thousand) (sub-units true thousand)
      :always                     ((comp drop-leading-zero
                                         flatten
                                         med-num)))))

(defn valid? [num]
  (when (not (< -1 num 999999999999))
    (throw (IllegalArgumentException. "Number out of bounds."))))

(defn number [num]
  (valid? num)
  (if (= num 0)
    "zero"
    (let [parts (hi-num num)
          ps (if (= (count (filter int? parts)) 0)
               parts
               (flatten (map #(if (int? %)
                                (number %)
                                %) parts)))
          joined (str/join " " ps)]
    (str/replace joined " -" "-"))))
