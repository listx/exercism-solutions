(ns protein-translation)

(def codons
  {"AUG" "Methionine"
   "UUU" "Phenylalanine"
   "UUC" "Phenylalanine"
   "UUA" "Leucine"
   "UUG" "Leucine"
   "UCU" "Serine"
   "UCC" "Serine"
   "UCA" "Serine"
   "UCG" "Serine"
   "UAU" "Tyrosine"
   "UAC" "Tyrosine"
   "UGU" "Cysteine"
   "UGC" "Cysteine"
   "UGG" "Tryptophan"
   "UAA" "STOP"
   "UAG" "STOP"
   "UGA" "STOP"})

(def translate-codon codons)

(defn translate-rna [rna]
  (->> (partition 3 rna)
       (map #(apply str %))
       (map translate-codon)
       (take-while #(not= "STOP" %))))
