(ns secret-handshake)

(defn commands [nbr]
  (->> ["wink" "double blink" "close your eyes" "jump" reverse]
       ;; map-indexed is like Python's `enumerate`.
       (map-indexed vector)
       (reduce
        (fn [acc [bit x]]
          ;; Create a function which can either append into a collection, or be
          ;; itself. In our case, the `reverse` item will be itself, while the
          ;; string elements will all become "appenders", so to speak.
          (let [f (if (string? x)
                    #(conj % x)
                    x)]
            (cond-> acc
              ;; Thankfully, bit-test uses 0-based indexing, so we can use the
              ;; indices from `map-indexed` (which start at 0) directly.
              (bit-test nbr bit) f)))
        [])))
