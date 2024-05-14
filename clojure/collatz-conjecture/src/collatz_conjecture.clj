(ns collatz-conjecture)

(defn collatz
  [n]
  (loop [i 0
         x n]
    (cond
      (< x 1) (throw (IllegalArgumentException. "Number out of bounds."))
      (= 1 x) i
      :else   (recur (inc i)
                     (if (even? x)
                       (bit-shift-right x 1)
                       (inc (* 3 x)))))))
