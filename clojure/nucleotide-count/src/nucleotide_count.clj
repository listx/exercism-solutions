(ns nucleotide-count
  (:require [clojure.string :refer [includes?]]))

(defn count-of-nucleotide-in-strand [nucleotide strand]
  (when-not (includes? "ACGT" (str nucleotide))
    (throw (IllegalArgumentException.)))
  (->> (seq strand)
       (filter #(= nucleotide %))
       count))

(defn nucleotide-counts [strand]
  (reduce (fn [acc nucleotide]
            (if (contains? acc nucleotide)
              (update acc nucleotide inc)
              acc))
          {\A 0
           \C 0
           \G 0
           \T 0}
          (seq strand)))
