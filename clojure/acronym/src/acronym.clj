(ns acronym
  (:require [clojure.string :as str]))

(defn acronym
  "Converts phrase to its acronym."
  [phrase]
  (->> (re-seq #"[A-Z]+[a-z]*|[a-z]+" phrase)
       (map (comp str/capitalize first))
       str/join))
