(ns elyses-destructured-enchantments)

(defn first-card
  "Returns the first card from deck."
  [deck]
  (let [[a] deck] a))

(defn second-card
  "Returns the second card from deck."
  [deck]
  (let [[_ b] deck] b))

(defn swap-top-two-cards
  "Returns the deck with first two items reversed."
  [deck]
  (let [[a b & rest] deck]
    (concat [b a] rest)))

(defn discard-top-card
  "Returns a sequence containing the first card and
   a sequence of the remaining cards in the deck."
  [deck]
  (let [[a & rest] deck]
    [a rest]))

(def face-cards
  ["jack" "queen" "king"])

(defn insert-face-cards
  "Returns the deck with face cards between its head and tail."
  [deck]
  (concat (take 1 deck) face-cards (drop 1 deck)))
