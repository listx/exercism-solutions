(ns nth-prime)

(def primes
  (let [nums (iterate (partial + 2) 3)
        factors-to-try (fn [x] (take-while #(<= (* % %) x) nums))
        prime? (fn [x] (every? #(pos? (mod x %)) (factors-to-try x)))]
    (filter prime? (concat [2] nums))))

(defn nth-prime 
  "Returns the prime number in the nth position."
  [n]
  (when-not (pos? n)
    (throw (IllegalArgumentException.)))
  (nth primes (dec n)))
