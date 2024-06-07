(ns binary)

(defn binary? [s]
  (re-matches #"^[01]+$" s))

(defn to-decimal [s]
  (if (binary? s)
    (->> s
         (map {\0 0
               \1 1})
         reverse
         (map vector (iterate (partial * 2) 1))
         (map (partial apply *))
         (apply +))
    0))
