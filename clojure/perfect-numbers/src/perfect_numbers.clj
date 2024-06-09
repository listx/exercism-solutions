(ns perfect-numbers)

(defn aliquot-sum [n]
  (->> (range 1 n)
       (filter #(zero? (mod n %)))
       (apply +)))

(defn classify [n]
  (when (neg? n) (throw (IllegalArgumentException. "invalid input")))
  (case (compare (aliquot-sum n) n)
    -1 :deficient
    0  :perfect
       :abundant))
