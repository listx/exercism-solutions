(ns luhn)

(defn valid? [s]
  (let [stripped (->> s
                      (filter #(Character/isDigit %))
                      (apply str))]
    (cond
      ;; If invalid characters, invalid.
      (some false? (map #(or (Character/isSpaceChar %)
                             (Character/isDigit %)) s)) false
      ;; If too short, invalid.
      (< (count stripped) 2) false
      :else (->> stripped
                 (map #(Character/digit % 10))
                 reverse
                 (map vector (cycle [1 2]))
                 (map (partial apply *))
                 (map #(if (< 9 %)
                         (- % 9)
                         %))
                 (apply +)
                 (#(rem % 10))
                 zero?))))
