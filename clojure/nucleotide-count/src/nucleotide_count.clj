(ns nucleotide-count)

(def nucleotide?
  #{\A \C \G \T})

(def zero-counts (zipmap nucleotide? (repeat 0)))

(defn nucleotide-counts [strand]
  (->> (frequencies strand)
       (merge zero-counts)))

(defn count-of-nucleotide-in-strand [nucleotide strand]
  (when-not (nucleotide? nucleotide)
    (throw (IllegalArgumentException.)))
  ((nucleotide-counts strand) nucleotide))
