(ns bob
  (:require [clojure.string :as str]))

(defn has-words?
  "True if there are alphabetic characters in the string."
  [s]
  (re-find #"[\p{IsAlphabetic}]" s))

(defn shouting?
  "True if the string is considered a shouting (all-caps) sentence."
  [s]
  (and (has-words? s)
       (= s (str/upper-case s))))

(defn question?
  "True if the string represents a question (ends in question mark)."
  [s]
  (-> s
      str/trimr
      (str/ends-with? "?")))

(defn response-for [s]
  (cond
    (str/blank? s)      "Fine. Be that way!"
    (and (shouting? s)
         (question? s)) "Calm down, I know what I'm doing!"
    (shouting? s)       "Whoa, chill out!"
    (question? s)       "Sure."
    :else               "Whatever."))
