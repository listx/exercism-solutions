(ns nucleotide-count)

;; Use a set as a verb, because in Clojure the set data structure is also a
;; function.
(def nucleotide?
  #{\A \C \G \T})

(def zero-counts (zipmap nucleotide? (repeat 0)))

;; "frequencies" does all the hard work for us of counting occurrences of
;; elements. The `(merge zero-counts)` bit just ensures that we return zero
;; counts of unseen nucleotides.
(defn nucleotide-counts [strand]
  (->> (frequencies strand)
       (merge zero-counts)))

(defn count-of-nucleotide-in-strand [nucleotide strand]
  (when-not (nucleotide? nucleotide)
    (throw (IllegalArgumentException.)))
  ;; Reuse `nucleotide-counts` to check for the count of just one nucleotide.
  ((nucleotide-counts strand) nucleotide))
