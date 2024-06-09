(ns pascals-triangle)

;; See https://www.reddit.com/r/Clojure/comments/vuaif6/i_cant_believe_how_easy_pascals_triangle_is_in/.

(def triangle
  (iterate
    (fn [above]          ; 1 3 3 1
      ;; mapv is like Haskell's zipWith, but which supports arbitrary numbers of
      ;; collections. And +' is an arbitrary-precision version of +.
      (mapv +'

        ;; Offset the preceding row with a zero at the end.
        (conj above 0)   ; 1 3 3 1 0

        ;; Offset the preceding row with a zero at the beginning.
        (cons 0 above))) ; 0 1 3 3 1
    ;; First row is [1].
    [1]))                ; 1 4 6 4 1 <-- This is what we get when we add the
                         ;               columns together.

(defn row [n]
  (nth triangle (dec n)))
