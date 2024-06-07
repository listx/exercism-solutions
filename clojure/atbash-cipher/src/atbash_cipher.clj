(ns atbash-cipher
  (:require [clojure.string :refer [lower-case]]))

(def encoder
  (let [letters "abcdefghijklmnopqrstuvwxyz"]
    (zipmap letters (reverse letters))))

(defn encode [s]
  (->> s
       (reduce (fn [[out n] ch]
                 (let [addable (Character/isLetterOrDigit ch)]
                   (if addable
                     [(cond-> out
                        (and (pos? n) (zero? (rem n 5))) (str " ")
                        :always (str (if (Character/isLetter ch)
                                       (encoder (first (lower-case ch)))
                                       ch)))
                      (inc n)]
                     [out n])))
               ["" 0])
       first))
