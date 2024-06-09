(ns isbn-verifier
  (:require [clojure.string :as str]))

(defn helper [chars]
  (cond
    ;; Exactly 10 digits found?
    (not= (count chars) 10) false
    ;; For the first 9 chars, are they all digits?
    (not (every? #(Character/isDigit %) (take 9 chars))) false
    ;; For the last char, is it a digit or "X"?
    (not (or (Character/isDigit (nth chars 9))
             (= (nth chars 9) \X))) false
    :else
      (as-> chars x
        ;; Convert characters to numbers. The last "X", if it's there, will
        ;; become -1.
        (map #(Character/digit % 10) x)
        ;; Convert any -1 into 10, because that's what the "X" char means.
        (map #(if (neg? %) 10 %) x)
        ;; Convert to addends, sum them up, mod by 11, and check if the result
        ;; is 0.
        (map * (range 10 0 -1) x)
        (apply + x)
        (mod x 11)
        (zero? x))))

(defn isbn? [isbn]
  (->> (str/replace isbn "-" "")
       helper))
