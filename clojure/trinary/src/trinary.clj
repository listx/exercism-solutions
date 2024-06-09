(ns trinary)

;; Adapted from
;; https://exercism.org/tracks/clojure/exercises/hexadecimal/solutions/listx.
;; The only thing we need to change is the '16' base to just '3'.

(defn finalize [terms]
  (if (some neg? terms)
    0
    (apply + terms)))

(defn to-decimal [s]
  (->> (reverse s)
       (map #(Character/digit % 3))
       (map * (iterate #(* 3 %) 1))
       finalize))
