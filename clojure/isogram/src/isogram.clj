(ns isogram
  (:require [clojure.string :as str]))

(defn isogram? [s]
  (->> (#(str/replace s #"[ -]" ""))
       str/lower-case
       frequencies
       vals
       (every? #(= 1 %))))
