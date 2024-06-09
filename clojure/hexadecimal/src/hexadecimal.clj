(ns hexadecimal)

(defn finalize [terms]
  (if (some #(= :invalid-char %) terms)
    0
    (apply + terms)))

(defn hex-to-int [s]
  (->> (reverse s)
       (map vector (iterate #(* 16 %) 1) )
       (map (fn [[coeff ch]]
              (case ch
                \0 (* coeff  0)
                \1 (* coeff  1)
                \2 (* coeff  2)
                \3 (* coeff  3)
                \4 (* coeff  4)
                \5 (* coeff  5)
                \6 (* coeff  6)
                \7 (* coeff  7)
                \8 (* coeff  8)
                \9 (* coeff  9)
                \a (* coeff 10)
                \b (* coeff 11)
                \c (* coeff 12)
                \d (* coeff 13)
                \e (* coeff 14)
                \f (* coeff 15)
                :invalid-char)))
       finalize))
