(ns nth-prime)

;; See the paper "The Genuine Sieve of Eratosthenes" by
;; Melissa E. O’Neill at https://www.cs.hmc.edu/~oneill/papers/Sieve-JFP.pdf,
;; particularly for the discussion about the `wheel`. Sadly the generation of
;; the wheel itself is left out of the paper.
;;
;; The algorithm here makes use of O'Neill's "trial division" algorithm on page
;; 3, together with the "wheel2357" shown on page 8 of the paper.
(def ^{:private true} primes
  (concat
    [2 3 5 7]
    ;; See https://clojure-doc.org/articles/language/laziness/ for a discussion
    ;; of lazy-seq. Basically it allows us to make `primes` a lazy sequence.
    ;;
    ;; In this case, removing this outer lazy-seq results in an error. So the
    ;; inner `lazy-seq` (inside `primes-from`) can be thought of as the
    ;; "required one", and the one here is (probably?) required to keep it lazy
    ;; at the outermost level.
    (lazy-seq
      (let [factors-to-try (fn [x] (take-while #(<= (* % %) x) primes))
            primes-from
             (fn primes-from [x [wheel-head & wheel-tail]]
               (if (every? #(pos? (mod x %)) (factors-to-try x))
                 ;; The candidate `x` is prime! Prepend it to the list of primes
                 ;; we are generating with `primes-from`.
                 (lazy-seq (cons x
                                 (primes-from (+ x wheel-head)
                                              wheel-tail)))
                 ;; If any previously-seen prime can divide the current
                 ;; candidate, skip it. We advance to the next candidate, which
                 ;; is (+ x wheel-head).
                 (recur (+ x wheel-head) wheel-tail)))
            ;; `wheel2357` here can generate a sequence of numbers that are not
            ;; divisible by 2, 3, 5, and 7. As an example, a wheel of numbers
            ;; not divisible by 2 can be created by starting from 3 and adding
            ;; 2. Another example: a wheel of 2 and 3 (list of numbers not
            ;; divisible by 2 and 3) can begin at 5 and alternatively add 2 and
            ;; 4. See page 8 of the paper.
            ;;
            ;; In summary the wheel can be used to "skip to the next factor we
            ;; need to check for divisibility".
            wheel2357 (cycle [2 4 2 4 6 2 6 4 2 4 6 6 2 6  4  2
                              6 4 6 8 4 2 4 2 4 8 6 4 6 2  4  6
                              2 6 6 4 2 4 6 2 6 4 2 4 2 10 2 10])]
        (primes-from 11 wheel2357)))))

(defn nth-prime
  "Returns the prime number in the nth position."
  [n]
  (when-not (pos? n)
    (throw (IllegalArgumentException.)))
  (nth primes (dec n)))
