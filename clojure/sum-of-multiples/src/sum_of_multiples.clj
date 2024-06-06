(ns sum-of-multiples)

(defn sum-of-multiples [base-vals level]
  (->> base-vals
       (mapcat #(range % level %))
       set
       (apply +)))
