(ns hamming)

(defn distance [s1 s2]
  (when (= (count s1) (count s2))
    (->> (map #(if (= % %2) 0 1) s1 s2)
         (apply +))))
