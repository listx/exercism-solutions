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

(defn allergies [n]
  (keep-indexed #(when (bit-test n %) %2)
                allergens))

(defn allergic-to? [n allergen]
  (->> (allergies n)
       (some #{allergen})))
