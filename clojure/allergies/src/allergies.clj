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
  [:eggs
   :peanuts
   :shellfish
   :strawberries
   :tomatoes
   :chocolate
   :pollen
   :cats])

(defn to-bits [n]
  (loop [num n
         result ()]
    (cond
      (zero? num) result
      :else (recur (quot num 2)
                   (cons (rem num 2) result)))))

(defn allergies [n]
  (->> (to-bits n)
       reverse
       (mapv (fn [allergen bit]
               (when (pos? bit)
                 allergen))
             allergens)
       (remove nil?)))

(defn allergic-to? [n allergen]
  (->> (allergies n)
       (some #{allergen})))
