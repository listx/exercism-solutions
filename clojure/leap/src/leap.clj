(ns leap)

(defn divisible-by? [period year]
  (zero? (mod year period)))

(defn leap-year? [year]
  (if (divisible-by? 100 year)
    (divisible-by? 400 year)
    (divisible-by? 4 year)))
