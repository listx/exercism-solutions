(ns acronym
  (:require [clojure.string :as str]))

(defn first-or-caps
  [word]
  (let [camel-case-caps (->> word
                             (re-seq #"[A-Z][^A-Z]+")
                             (map first)
                             str/join)
        first-letter (first word)]
    (if (pos? (count camel-case-caps))
      camel-case-caps
      first-letter)))

(defn acronym
  "Converts phrase to its acronym."
  [phrase]
  (let [words (str/split phrase #"[- ]")
        first-or-caps (map first-or-caps words)
        result (str/upper-case (str/join first-or-caps))]
    result))
