(ns isogram
  (:require [clojure.string :refer [lower-case]]))

(defn isogram? [s]
  (->> (lower-case s)
       (remove #{\space \-})
       (apply distinct?)))
