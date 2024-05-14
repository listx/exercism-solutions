(ns collatz-conjecture)

(defn collatz
  [n]
  (when (not (pos? n))
    (throw (IllegalArgumentException. "Number must be positive.")))
  (loop [i 0
         x n]
    (if (= 1 x)
      i
      (recur (inc i)
             (if (even? x)
               (bit-shift-right x 1)
               (inc (* 3 x)))))))
