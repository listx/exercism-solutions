(ns pangram
  (:require [clojure.string :as str]))

(def all-letters
  (into #{} "abcdefghijklmnopqrstuvwxyz"))

(defn pangram? [s]
  (as-> (str/lower-case s) x
    (str/replace x #"[^a-z]" "")
    (into #{} x)
    (= all-letters x)))
