(ns change)

(def issue
  ;; Memoize the anonymous function which defines `issue`. We could move out the
  ;; anonymous function into a named helper function, but then we would also
  ;; need to write `(declare issue)` because of the need to call `issue` from
  ;; inside the helper function before the definition of `issue`. So that's why
  ;; we just put everything into this one function.
  (memoize
   (fn [n coins]
     ;; Evaluate to nil if `n` ever reaches 0.
     (when-not (zero? n)
       (as-> coins x
         ;; Remove all coins that are too big to be useful. This helps us
         ;; optimize the search space.
         (filter #(<= % n) x)
         ;; If we are unable to make change (because there are no coins
         ;; that "fit" inside `n`), throw an exception.
         (if (empty? x)
           (throw (IllegalArgumentException. "cannot change"))
           x)
         ;; Recursion! Select one of the coins (that's the `cons %` part).
         ;; Then recurse by reducing `n` by the amount of the selected coin
         ;; (`(- n %)`). This just results in a recursion tree, sort of like
         ;; the classic (naive) recursive fibonacci algorithm. But because we
         ;; memoize each call, it's still fast.
         ;;
         ;; Also note that because we apply `cons` to each coin, we end up
         ;; with a list of lists (we end up nesting one level). When `n` reaches
         ;; 0, `issue` will evaluate to nil, ending the recursion. For
         ;; reference, `(cons 1 (cons 2 nil))` evaluates to `(1 2)`.
         (map #(cons % (issue (- n %) x)) x)
         ;; Choose the one list of coins where we used the fewest number of
         ;; coins. This unnests us out of the level we entered from the
         ;; `map` call above.
         (apply min-key count x)
         ;; Sort the coins inside the list.
         (sort x))))))
