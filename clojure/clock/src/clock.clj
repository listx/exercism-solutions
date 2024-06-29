(ns clock)

(defn clock->string [[h m]]
  (format "%02d:%02d" h m))

(defn add-hours
  "Add positive or negative hours to the clock. Handles overflow."
  [[h m] hours]
  [(mod (+ h hours) 24) m])

(defn add-minutes
  "Add positive or negative minutes to the clock. Handles overflow."
  [[h m] minutes]
  (let [m' (+ m minutes)
        minutes-final (mod m' 60)
        carry (quot m' 60)]
    (cond
      ;; Happy path. No need to handle carries.
      (<= 0 m' 59) [h m']
      ;; Reuse `add-hours` to handle carries.
      (pos? m') (add-hours [h minutes-final] carry)
      ;; For negative minutes, even just -1 (such that `carry` is 0) will still
      ;; push back the hour by 1. So we have to account for the off-by-one
      ;; calculation here.
      :else     (add-hours [h minutes-final] (dec carry)))))

(def add-time add-minutes)

(defn clock
  "Store a clock value from 0h0m to 23h59m. Use modular arithmetic to handle
  carries (overflow), both in the positive and negative directions. E.g., (clock
  0 -1) is the same as (clock 23 59)."
  [hours minutes]
  (let [clock-with-hours (add-hours [0 0] hours)
        clock-with-hours-and-minutes (add-minutes clock-with-hours minutes)]
    clock-with-hours-and-minutes))
