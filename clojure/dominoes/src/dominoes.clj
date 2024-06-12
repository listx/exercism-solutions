(ns dominoes)

(defn chains [n ds]
  (for [
        ;; Iterate through all indices in ds. This way we can check all dominoes
        ;; in `ds` to be candidates for growing the chain (and not just the
        ;; first one).
        i (range (count ds))
        :let [
              ;; `l` is the left part of the partition from `split-at`. We don't
              ;; really care about `l` because we don't modify it. We only use
              ;; `split-at` to try to find a domino that will "fit", which we
              ;; can eliminate from the search by removing it. That's why it's
              ;; missing from the `concat` call below.
              ;;
              ;; `[a b]` is the leftmost domino from the right part of the
              ;; partition from `split-at`.
              [l [[a b] & r]] (split-at i ds)
              ;; `m` is the matching number on the leftmost domino from the
              ;; chain-to-be-grown, which is the same as `n` (the right edge of
              ;; the existing chain we have so far).
              m (cond (= a n) b
                      (= b n) a)]
        ;; Only consider growths where there is a match.
        :when m]
    ;; For the grown chain, leave out [a b] because we "selected" it to grow the
    ;; chain.
    [m (concat l r)]))

(defn chain-end [b ds]
  ;; Use `some` to stop the search as soon as we find a valid chain that works.
  (some (fn [[b ds]]
          ;; Consider only "full" chains that have finished growing.
          (if (empty? ds)
            ;; The [b ds] means that `chains` will return a collection of pairs
            ;; where `b` is the right edge of the chain. So return `b` if `ds`
            ;; (dominoes we can use to grow the chain) is empty.
            b
            ;; If the chain being considered is not fully grown, recurse to grow
            ;; it more (by calling `chains`).
            (chain-end b ds)))
        (chains b ds)))

(defn can-chain? [[[a b] & ds]]
  (cond
    ;; If empty, then it's chainable.
    (nil? a) true
    ;; If only 1 domino, then it must have identical numbers on each side.
    (nil? ds) (= a b)
    ;; If multiple dominoes, the left side of this first domino must equal the
    ;; end of the chain (rightmost edge). The question then is building up a
    ;; valid chain such that we can use up all the dominoes. We need `b` to
    ;; start the chain, and `ds` to select the dominoes in the chain.
    :else (= a
             (chain-end b (vec ds)))))
