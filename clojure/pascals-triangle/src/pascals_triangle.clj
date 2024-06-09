(ns pascals-triangle)

;; See https://www.reddit.com/r/Clojure/comments/vuaif6/i_cant_believe_how_easy_pascals_triangle_is_in/.

(def triangle
  (iterate
    (fn [above]          ; 1 3 3 1

      ;; mapv is like Haskell's zipWith.
      (mapv +

        ;; Offset the preceding row with a zero at the end.
        (conj above 0)   ; 1 3 3 1 0

        ;; Offset the preceding row with a zero at the beginning.
        (cons 0 above))) ; 0 1 3 3 1

    ;; First row is [1N] (the N suffix makes the 1 an arbitrary-precision
    ;; integer).
    [1N]))               ; 1 4 6 4 1 <-- This is what we get when we add the
                         ;               columns together.

(defn row [n]
  (nth triangle (dec n)))
