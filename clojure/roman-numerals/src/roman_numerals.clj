(ns roman-numerals)

;; Convert an arabic number into Roman numerals, using brute force. We have a
;; list of all possible units of Roman numeral representations at each place
;; (thousands, hundreds, tens, ones), sorted largest to smallest. Then we just
;; go through this list and try to "fit" it into the number we are converting.
;; If the unit fits, we append the equivalent Roman numeral to our output
;; string, and subtract the equivalent amount from the input number. We keep
;; going until we are done iterating through the entire `romans` list.

(def ^:private romans
  [[3000 "MMM" ]
   [2000 "MM"  ]
   [1000 "M"   ]
   [ 900 "CM"  ]
   [ 800 "DCCC"]
   [ 700 "DCC" ]
   [ 600 "DC"  ]
   [ 500 "D"   ]
   [ 400 "CD"  ]
   [ 300 "CCC" ]
   [ 200 "CC"  ]
   [ 100 "C"   ]
   [  90 "XC"  ]
   [  80 "LXXX"]
   [  70 "LXX" ]
   [  60 "LX"  ]
   [  50 "L"   ]
   [  40 "XL"  ]
   [  30 "XXX" ]
   [  20 "XX"  ]
   [  10 "X"   ]
   [   9 "IX"  ]
   [   8 "VIII"]
   [   7 "VII" ]
   [   6 "VI"  ]
   [   5 "V"   ]
   [   4 "IV"  ]
   [   3 "III" ]
   [   2 "II"  ]
   [   1 "I"   ]])

(defn- fit-roman [[balance s]
                  [unit roman]]
  (if (<= unit balance)
    [(- balance unit) (str s roman)]
    [balance s]))

(defn numerals [n]
  (->> (reduce fit-roman [n ""] romans)
       second))
