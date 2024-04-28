(ns interest-is-interesting)

(defn interest-rate
  "Returns the interest rate based on the specified balance."
  [balance]
  (cond (>= balance 5000) 2.475
        (>= balance 1000) 1.621
        (>= balance 0)    0.5
        :else             -3.213))

(defn annual-balance-update
  "Returns the annual balance update, taking into account the interest rate."
  [balance]
  ; Using `abs` here keeps the value negative if the interest rate was negative.
  ; Then we add this to the original balance. Adding two negative numbers also
  ; preserves the sign (keeps it negative), so we don't have to bother branching
  ; on `pos?`.
  (->> (interest-rate balance)
       bigdec
       (* 0.01M (abs balance))
       (+ balance)))

(defn amount-to-donate
  "Returns how much money to donate based on the balance and the tax-free percentage."
  [balance tax-free-percentage]
  (->> tax-free-percentage
       bigdec
       (* 2 0.01M balance)
       int
       (max 0)))
