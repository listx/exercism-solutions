(ns rotational-cipher)

(defn- lowercase? [c]
  (<= (int \a) (int c) (int \z)))

(defn- uppercase? [c]
  (<= (int \A) (int c) (int \Z)))

(defn- encodable? [c]
  (or (lowercase? c)
      (uppercase? c)))

(defn- caesar-encode [c offset]
  (if (encodable? c)
    (let [start-idx (int (if (uppercase? c) \A \a))]
      (-> (- (int c) start-idx)
          (+ offset)
          (mod 26)
          (+ start-idx)
          char))
    c))

(defn rotate
  "Rotate letters by the given offset. Leave non-letter characters as is."
  [plaintext offset]
  (reduce #(str % (caesar-encode %2 offset)) "" plaintext))
