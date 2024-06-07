(ns binary)

(defn to-decimal [s]
  (->> s
       (map #({\0 0
               \1 1} % 0))
       reverse
       (map * (iterate (partial * 2) 1))
       (apply +)))
