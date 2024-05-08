(ns triangle)

(defn is-valid? [a b c]
  (and (> (+ a b) c)
       (> (+ a c) b)
       (> (+ b c) a)
       (pos? a)
       (pos? b)
       (pos? c)))

(defn equilateral?
  [a b c]
  (and
    (is-valid? a b c)
    (= a b c)))

(defn isosceles? [a b c]
  (and
    (is-valid? a b c)
    (or
      (= a b)
      (= a c)
      (= b c))))

(defn scalene? [a b c]
  (and
    (is-valid? a b c)
    (not= a b)
    (not= a c)
    (not= b c)))
