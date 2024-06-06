(ns word-count
  (:require [clojure.string :refer [lower-case]]))

(defn word-count [s]
  (->> s
       (re-seq #"\w+")
       (map lower-case)
       frequencies))
