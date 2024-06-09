(ns prime-factors)

(defn of [n]
  (loop [[factor & rst :as factors] (iterate inc 2)
         remaining n
         result []]
    (cond
      (= 1 remaining) result
      (zero? (rem remaining factor)) (recur factors
                                            (quot remaining factor)
                                            (conj result factor))
      :else                          (recur rst
                                            remaining
                                            result))))
