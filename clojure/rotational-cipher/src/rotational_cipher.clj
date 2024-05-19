(ns rotational-cipher)

(def ^:private lowercase "abcdefghijklmnopqrstuvwxyz")
(def ^:private uppercase "ABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn- caesar-cipher [offset]
  (let [lower-encoder (zipmap lowercase
                              (drop offset (cycle lowercase)))
        upper-encoder (zipmap uppercase
                              (drop offset (cycle uppercase)))
        cipher (merge lower-encoder
                      upper-encoder)]
    ;; Recall that in Clojure a map is a function which returns the value if the
    ;; given key (first argument) is found. If we provide an additional (second)
    ;; argument to the map, it acts as a default return value in case the key
    ;; (first argument) is not found in the map. So here we basically have a map
    ;; that returns the encoded version of a letter if the `cipher` supports it,
    ;; but otherwise returns the given letter unchanged.
    #(cipher % %)))

(defn rotate
  "Rotate letters by the given offset. Leave non-letter characters as is."
  [plaintext offset]
  (apply str (map (caesar-cipher offset) plaintext)))
