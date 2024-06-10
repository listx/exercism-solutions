(ns allergies)

;; eggs (1)
;; peanuts (2)
;; shellfish (4)
;; strawberries (8)
;; tomatoes (16)
;; chocolate (32)
;; pollen (64)
;; cats (128)

(def allergens
  (zipmap (iterate (partial * 2) 1)
          [:eggs
           :peanuts
           :shellfish
           :strawberries
           :tomatoes
           :chocolate
           :pollen
           :cats]))

(defn to-binary-digits [n]
  (loop [num n
         result ()]
    (cond
      (zero? num) result
      :else (recur (quot num 2)
                   (cons (rem num 2) result)))))

(defn allergies [n]
  (->> (to-binary-digits n)
       reverse
       (map * (iterate (partial * 2) 1))
       (keep allergens)))

(defn allergic-to? [n allergen]
  (->> (allergies n)
       (keep #{allergen})
       seq))
