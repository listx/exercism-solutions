(ns anagram
  (:require [clojure.string :as str]))

(defn anagram?
  "Return true if string `s1` is an anagram of `s2` (or vice versa)."
  [s1 s2]
  (and
   (not= (str/lower-case s1) s2)
   (= (sort (into [] (str/lower-case s1)))
      (sort (into [] (str/lower-case s2))))))

(defn anagrams-for [word prospect-list]
  (filter #(anagram? word %) prospect-list))
