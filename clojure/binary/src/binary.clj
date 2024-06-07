(ns binary)

(defn to-decimal [s]
  (->> s
       (map #({\0 0
               \1 1} % 0))
       reverse
       (map vector (iterate (partial * 2) 1))
       (map (partial apply *))
       (apply +)))
