(ns pig-latin
  (:require [clojure.string :as str]))

(defn starts-with-vowel? [s]
  (or
    (#{\a \e \i \o \u} (first s))
    (str/starts-with? s "xr")
    (str/starts-with? s "yt")))

(defn flipped-y [s]
  (->> (re-find #"^([^aeiou]+)(y.*)" s)
       (drop 1)
       reverse
       (apply str)))

(defn flipped-leading-consonant-cluster [s]
  (->> (re-find #"^([^aeiou]+)(.*)" s)
       (drop 1)
       reverse
       (apply str)))

(defn translate-word [s]
  (let [flipped (flipped-leading-consonant-cluster s)
        flipped-with-y (flipped-y s)]
  (cond
    (starts-with-vowel? s) (str s "ay")
    flipped (if (and (str/ends-with? flipped "q") (= \u (first flipped)))
              (str (apply str (drop 1 flipped)) \u "ay")
              (str flipped "ay"))

    (and flipped-with-y (str/includes? s "y")) (str flipped-with-y "ay")
    :else s)))

(defn translate [s]
  (->> (str/split s #" ")
    (map translate-word)
    (str/join " ")))
