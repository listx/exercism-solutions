(ns prime-factors)

(defn of [n]
  (loop [factor 2
         remaining n
         result []]
    (cond
      (= 1 remaining) result
      (zero? (rem remaining factor)) (recur factor
                                            (quot remaining factor)
                                            (conj result factor))
      :else                          (recur (inc factor)
                                            remaining
                                            result))))
