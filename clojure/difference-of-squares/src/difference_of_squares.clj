(ns difference-of-squares)

(defn sum-of-squares [n]
  (->> (range 1 (inc n))
       (map #(* % %))
       (apply +)))

(defn square-of-sum [n]
  (->> (range 1 (inc n))
       (apply +)
       (#(* % %))))

(defn difference [n]
  (- (square-of-sum n)
     (sum-of-squares n)))
