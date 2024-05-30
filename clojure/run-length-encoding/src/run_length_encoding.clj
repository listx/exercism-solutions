(ns run-length-encoding)

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (->> (partition-by identity plain-text)
       (reduce (fn [acc, elt]
                 (str acc
                      (when (< 1 (count elt)) (count elt))
                      (first elt)))
               "")))

; See https://stackoverflow.com/a/18624940/437583 for more.
(defn- digit? [c]
  (and (>= 0 (compare \0 c))
       (>= 0 (compare c \9))))

(defn- expand [s]
  (if (digit? (first s))
    (let [[n [c]] (split-with digit? s)]
      (->> (repeat (Integer. (apply str n)) c)
           (apply str)))
    s))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [cipher-text]
  (->> (re-seq #"[A-Za-z ]+|[0-9]+[A-Za-z ]" cipher-text)
       (reduce (fn [acc, elt]
                 (str acc (expand elt)))
               "")))
