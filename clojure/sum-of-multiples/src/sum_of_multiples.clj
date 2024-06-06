(ns sum-of-multiples)

(defn multiples-of [n]
  (map #(* n %) (iterate inc 1)))

(defn sum-of-multiples [base-vals level]
  (->> base-vals
       (map multiples-of)
       (map (fn [multiples]
              (take-while #(< % level)
                          multiples)))
       flatten
       set
       (apply +)))
