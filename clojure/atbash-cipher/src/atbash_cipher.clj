(ns atbash-cipher
  (:require [clojure.string :refer [join lower-case]]))

(def encoder
  (let [letters "abcdefghijklmnopqrstuvwxyz"]
    (zipmap letters (reverse letters))))

(defn encode [s]
  (->> s
       lower-case
       (filter #(Character/isLetterOrDigit %))
       (map #(encoder % %))
       (partition-all 5)
       (map (partial apply str))
       (join " ")))
