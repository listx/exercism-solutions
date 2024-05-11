(ns armstrong-numbers)

(defn pow [a b] (reduce * (repeat b a)))

(defn armstrong?
  "To determine whether a number is an armstrong number, we first break it up
  into separate digits. Then for each digit, we raise it to a power equal to the
  total number of digits. The sum of these powers must equal the original number."
  [num]
  (let [
        ;; Given a number 1025, base-10-parts is '(1025 102 10 1)
        base-10-parts (take-while pos? (iterate #(quot % 10) num))

        ;; and digit-list is '(5 2 0 1)
        digit-list (map #(mod % 10) base-10-parts)

        ;; and num-digits is 4
        num-digits (count digit-list)

        ;; and power-sums is '((pow 5 4) (pow 2 4)
        ;;                     (pow 0 4) (pow 1 4))
        ;;
        ;; or
        ;;
        ;;'(625 15 0 1).
        power-sums (map #(pow % num-digits) digit-list)

        sum (reduce + power-sums)]
    (= num sum)))
