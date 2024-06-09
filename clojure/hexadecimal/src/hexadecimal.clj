(ns hexadecimal)

(defn finalize [terms]
  (if (some neg? terms)
    0
    (apply + terms)))

(defn hex-to-int [s]
  (->> (reverse s)
       (map #(Character/digit % 16))
       (map * (iterate #(* 16 %) 1))
       finalize))
