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
  (let [interest (->> balance bigdec interest-rate bigdec (* 0.01M) (* balance))]
    (if (pos? balance)
      (+ balance interest)
      (- balance interest))))

(defn amount-to-donate
  "Returns how much money to donate based on the balance and the tax-free percentage."
  [balance tax-free-percentage]
  (if (pos? balance)
    (->> tax-free-percentage bigdec (* 2) (* 0.01M) (* balance) int)
    0))
