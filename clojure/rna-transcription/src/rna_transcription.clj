(ns rna-transcription
  (:require [clojure.string :as str]))

(def dna->rna
  {\G \C
   \C \G
   \T \A
   \A \U})

(defn valid-dna? [dna]
  (every? dna->rna dna))

(defn to-rna [dna]
  (when-not (valid-dna? dna)
    (throw (AssertionError.)))
  (->> (seq dna)
       (map dna->rna)
       (apply str)))
