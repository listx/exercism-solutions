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
       (mapv (partial apply str))))

(defn pad-with-trailing-spaces [s n]
  (take n (concat s (repeat \space))))

(defn ciphertext-rect [s]
  (->> (plaintext-segments s)
       (#(conj (into '[] (butlast %))
               (pad-with-trailing-spaces (last %)
                                         (count (first %)))))
       (apply map str)))

(defn ciphertext [s]
  (->> (ciphertext-rect s)
       (apply str)
       (#(str/replace % " " ""))))

(defn normalize-ciphertext [s]
  (->> (ciphertext-rect s)
       (str/join " ")))
