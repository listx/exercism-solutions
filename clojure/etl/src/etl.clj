(ns etl
  (:require [clojure.string :refer [lower-case]]))

(defn unpack
  "Unpacks a map where each entry looks like [score strs] (score is the key and
  strs is the value). This returns a collection."
  [[score strs]]
  (map
   #(vector (lower-case %) score)
   strs))

(defn transform
  "Convert a map of score-to-uppercase-letters to a map of
  lowercase-letters-to-score entries."
  [source]
  ;; Because `unpack` returns a collection, we have to concatenate the results
  ;; into a single collection with mapcat.
  (->> (mapcat unpack source)
       (into {})))
