(ns acronym
  (:require [clojure.string :as str]))

(defn acronym
  "Converts phrase to its acronym."
  [phrase]
  (->> phrase
       (re-seq #"[A-Z]+[a-z]*|[a-z]+")
       (map first)
       str/join
       str/upper-case))
