(ns armstrong-numbers
  ;; To get the `expt` function, do
  ;;
  ;;   neil dep add :lib org.clojure/math.numeric-tower :version "0.1.0"
  ;;   clj -X:deps prep
  ;;
  ;; and in the CIDER repl you can do
  ;;
  ;;  user> (clojure.math.numeric-tower/expt 2 5)
  ;;  ;; => 32
  ;;
  (:require [clojure.math.numeric-tower :as math]))

(defn armstrong?
  "To determine whether a number is an armstrong number, we first break it up
  into separate digits. Then for each digit, we raise it to a power equal to the
  total number of digits. The sum of these powers must equal the original number."
  [num]
  (let [
        ;; Given a number 1025, base-10-parts is '(1025 102 10 1)...
        base-10-parts (take-while pos? (iterate #(quot % 10) num))
        ;; ... and num-digits would be 4 ...
        num-digits (count base-10-parts)
        ;; ... and digit-list would be '(5 2 0 1) ...
        digit-list (map #(mod % 10) base-10-parts)
        ;; ... and power-sums would be '((math/expt 5 4) (math/expt 2 4)
        ;;                               (math/expt 0 4) (math/expt 1 4))
        ;;
        ;; or
        ;;
        ;;'(625 15 0 1)
        power-sums (map #(math/expt % num-digits) digit-list)
        sum (reduce + power-sums)]
    (= num sum)))
