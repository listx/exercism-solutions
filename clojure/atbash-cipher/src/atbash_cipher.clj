(ns atbash-cipher
  (:require [clojure.string :refer [join lower-case]]))

(def encoder
  (let [letters "abcdefghijklmnopqrstuvwxyz"]
    (zipmap letters (reverse letters))))

(defn encode [s]
  (->> s
       (filter #(Character/isLetterOrDigit %))
       ;; We have to use `first` because `lower-case` returns a string, not a
       ;; character.
       (map (comp first lower-case))
       (map #(or (encoder %) %))
       (partition-all 5)
       (map (partial apply str))
       (join " ")))
