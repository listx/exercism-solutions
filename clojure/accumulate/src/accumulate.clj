(ns accumulate)

(defn accumulate [op coll]
  ;; The `#(conj % (op %2))` is the function to `reduce`, which is required to
  ;; take 2 arguments. The first argument is the accumulator (in our case, a
  ;; vector) and the second argument is the next element to add into the vector.
  ;; Here we call `(op %2)` to generate the next element. The `conj` simply
  ;; appends to the accumulator.
  (reduce #(conj % (op %2)) [] coll))
