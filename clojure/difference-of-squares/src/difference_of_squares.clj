(ns difference-of-squares)

(defn sum-of-squares [n]
  (quot (* n (inc n) (inc (* 2 n))) 6))

(defn square-of-sum [n]
  (->> (quot (* n (inc n)) 2)
       (#(* % %))))

(defn difference [n]
  (- (square-of-sum n)
     (sum-of-squares n)))
