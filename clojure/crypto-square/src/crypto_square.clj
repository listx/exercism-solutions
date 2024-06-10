(ns crypto-square
  (:require [clojure.string :as str]))

(defn normalize-plaintext [s]
  (->> (str/lower-case s)
       (filter #(Character/isLetterOrDigit %))
       (apply str)))

(defn rect-size [s]
  (let [len (count s)]
    (->> (for [r (range 1 len) c (range 1 len)
               :when (and (<= r c)
                          (<= (- c r) 1)
                          (<= len (* r c)))]
           [r c])
         first)))

(defn square-size [s]
  (->> (rect-size s)
       second))

(defn plaintext-segments [s]
  (->> (normalize-plaintext s)
       (#(partition-all (square-size %) %))
       (map (partial apply str))))

(defn ciphertext-segments [s]
  (->> (normalize-plaintext s)
       (#(partition (square-size %)
                    (square-size %)
                    (repeat \space) %))
       (apply map str)))

(defn ciphertext [s]
  (->> (ciphertext-segments s)
       (map str/trim)
       str/join))

(defn normalize-ciphertext [s]
  (->> (ciphertext-segments s)
       (str/join " ")))
