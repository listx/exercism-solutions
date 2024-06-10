(ns phone-number)

(defn number
  [s]
  (let [normalized (->> s
                        (filter #(Character/isDigit %))
                        (#(cond->> %
                            (= 10 (count %)) (cons \1))))
        country-code  (nth normalized 0)
        area-code     (nth normalized 1)
        exchange-code (nth normalized 4)
        invalid "0000000000"]
    (cond
      (not= 11 (count normalized)) invalid
      (not= \1 country-code)       invalid
      (#{\0 \1} area-code)         invalid
      (#{\0 \1} exchange-code)     invalid
      :else (->> normalized
                 (drop 1)
                 (apply str)))))
