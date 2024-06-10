(ns octal)

;; Adapted from
;; https://exercism.org/tracks/clojure/exercises/hexadecimal/solutions/listx.
;; The only thing we need to change is the '16' base to just '8'.

(defn finalize [terms]
  (if (some neg? terms)
    0
    (apply + terms)))

(defn to-decimal [s]
  (->> (reverse s)
       (map #(Character/digit % 8))
       (map * (iterate #(* 8 %) 1))
       finalize))
