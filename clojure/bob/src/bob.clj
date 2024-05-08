(ns bob
  (:require [clojure.string :as str]))

(defn has-words?
  "True if there are alphabetic characters in the string."
  [s]
  (let [pat #".*[\p{IsAlphabetic}].*"
        matches (re-matches pat s)]
    (pos? (count matches))))

(defn shouting?
  "True if the string is considered a shouting (all-caps) sentence."
  [s]
  (and (= s (str/upper-case s))
       (has-words? s)))

(defn question?
  "True if the string represents a question (ends in question mark)."
  [s]
  (-> s
      str/trimr
      (str/ends-with? "?")))

(defn response-for [s] ;; <- arglist goes here
  (cond
    (= "" (str/trim s)) "Fine. Be that way!"
    (and (shouting? s) (question? s)) "Calm down, I know what I'm doing!"
    (shouting? s) "Whoa, chill out!"
    (question? s) "Sure."
    :else "Whatever."))
