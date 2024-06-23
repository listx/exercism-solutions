(ns dominoes)

(defn grow-chains [dots ds]
  (for [
        ;; Iterate through all indices in ds. This way we can check all dominoes
        ;; in `ds` to be candidates for growing the chain (and not just the
        ;; first one).
        i (range (count ds))
        :let [
              ;; `l` is the left part of the partition from `split-at`. We don't
              ;; really care about `l` because we don't modify it. We only use
              ;; `split-at` to try to find a domino (`[a b]`) that will "fit",
              ;; which we can eliminate from the search by removing it. That's
              ;; why `[a b]` is missing from the `concat` call below.
              ;;
              ;; `[a b]` is the leftmost domino from the right part of the
              ;; partition from `split-at`.
              [l [[a b] & r]] (split-at i ds)
              ;; For [a b], if either a or b matches `dots`, then
              ;; `dots-complement` is b or a, respectively (the *other* side of
              ;; the matching domino).
              dots-complement (cond (= a dots) b
                                    (= b dots) a)]
        ;; Only consider growths where there is a match.
        :when dots-complement]
    ;; For the grown chain, leave out [a b] because we "selected" it to grow the
    ;; chain.
    [dots-complement (concat l r)]))

(defn chain-end [b ds]
  ;; Use `some` to stop the search as soon as we find a valid chain that works.
  (some (fn [[dots ds]]
          ;; Consider only "full" chains that have finished growing.
          (if (empty? ds)
            ;; The [dots ds] means that `grow-chains` will return a collection
            ;; of pairs where `dots` is the right edge of the chain. So return
            ;; `dots` if `ds` (dominoes we can use to grow the chain) is empty.
            dots
            ;; If the chain being considered is not fully grown, recurse to grow
            ;; it more (by calling `grow-chains`).
            (chain-end dots ds)))
        (grow-chains b ds)))

(defn can-chain? [[[a b] & ds]]
  (cond
    ;; If empty, then it's chainable.
    (nil? a) true
    ;; If only 1 domino, then it must have identical numbers on each side.
    (nil? ds) (= a b)
    ;; If multiple dominoes, the left side of this first domino (`a`) must equal
    ;; the end of the chain (rightmost edge). The question then is building up a
    ;; valid chain such that we can use up all the dominoes. We need `b` to
    ;; start the chain, and `ds` to select the dominoes in the chain.
    :else (= a
             (chain-end b ds))))
