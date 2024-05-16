(ns pangram
  (:require [clojure.string :as str]))

(def all-letters
  (set "abcdefghijklmnopqrstuvwxyz"))

(defn pangram? [s]
  (as-> (str/lower-case s) x
    (str/replace x #"[^a-z]" "")
    (set x)
    (= all-letters x)))
