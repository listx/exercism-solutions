(ns collatz-conjecture)

(defn collatz
  [n]
  (loop [i 0
         x n]
    (cond
      (not (pos? n)) (throw (IllegalArgumentException.
                             "Number must be positive."))
      (= 1 x) i
      :else (recur (inc i)
                   (if (even? x)
                     (bit-shift-right x 1)
                     (inc (* 3 x)))))))
