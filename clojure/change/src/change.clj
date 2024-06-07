(ns change)

(defn largest-fitting-coin [sum coins]
  (->> coins
       (into '())
       sort
       reverse
       (drop-while #(< sum %))
       first))

(defn issue [sum coins]
  (when (neg? sum)
    (throw (IllegalArgumentException. "cannot change")))
  (when (< sum (->> coins (into '()) sort first))
    (throw (IllegalArgumentException. "cannot change")))

  (loop [remaining sum
         coin (largest-fitting-coin sum coins)
         result ()]
    (if (zero? remaining)
      result
      (recur (- remaining coin)
             (largest-fitting-coin (- remaining coin) coins)
             (cons coin result)))))
