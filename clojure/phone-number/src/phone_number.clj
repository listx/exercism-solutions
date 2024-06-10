(ns phone-number)

(defn number
  [s]
  (let [
        ;; Normalize the input to always have a "1" country code if it is
        ;; missing.
        normalized (->> s
                        (filter #(Character/isDigit %))
                        (#(cond->> %
                            (= 10 (count %)) (cons \1))))
        ;; Extract special positions from the number string. If they are missing
        ;; (the input is too short), then use \0 as the default so that it'll
        ;; trigger one of the validation failures below.
        country-code  (nth normalized 0 \0)
        area-code     (nth normalized 1 \0)
        exchange-code (nth normalized 4 \0)
        ;; Return "0000000000" if we detect invalid input.
        invalid-input "0000000000"]
    (cond
      (not= 11 (count normalized)) invalid-input
      (not= \1 country-code)       invalid-input
      (#{\0 \1} area-code)         invalid-input
      (#{\0 \1} exchange-code)     invalid-input
      :else (->> normalized
                 ;; Discard "1" country code.
                 (drop 1)
                 (apply str)))))
