(ns pig-latin
  (:require [clojure.string :as str]))

(def vowel? (set "aeiou"))

(def consonant? (complement vowel?))

(def non-y-consonant?
  "Is this letter a consonant that is also not 'y'?"
  (every-pred consonant? (partial not= \y)))

(defn special-leading-vowel-cluster? [word]
  (some (partial str/starts-with? word)
        ["xr" "yt"]))

;; "cc" here means "consonant cluster".
(defn y-after-cc? [cc rst]
  (and (empty? cc)
       (= \y (first rst))))

(defn qu-after-cc? [cc rst]
  (and (= \q (last cc))
       (= \u (first rst))))

(defn split-consonant [word]
  (let [[cc tail] (->> (split-with non-y-consonant? word)
                       (map (partial apply str)))
        ;; `tail-chopped` is the tail without its first letter.
        tail-chopped (apply str (rest tail))]
    (cond
      (special-leading-vowel-cluster? word) [word]
      ;; For a word with a "y" that follows a consonant cluster, move the "y" to
      ;; the end.
      (y-after-cc? cc tail) [tail-chopped "y"]
      ;; For "square", tail-chopped is "are" and "cc" is "sq". Move the "u" to
      ;; the end.
      (qu-after-cc? cc tail) [tail-chopped cc "u"]
      ;; Move the leading consonant cluster to the end.
      :else [tail cc])))

(defn piggify [word]
  ;; A piggified word always ends in "ay".
  (->> (conj (split-consonant word) "ay")
       (apply str)))

(defn translate [phrase]
  (->> (str/split phrase #" ")
       (map piggify)
       (str/join " ")))
