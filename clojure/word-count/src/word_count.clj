(ns word-count
  (:require [clojure.string :refer [lower-case]]))

(defn word-count [s]
  (->> s
       (re-seq #"[A-Za-z0-9]([A-Za-z0-9']+)?")
       (map first)
       (map lower-case)
       frequencies))
