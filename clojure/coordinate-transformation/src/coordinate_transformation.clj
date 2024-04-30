(ns coordinate-transformation)

(defn translate2d 
  "Returns a function making use of a closure to
   perform a repeatable 2d translation of a coordinate pair."
  [dx dy]
  (fn [x y] [(+ x dx) (+ y dy)]))

(defn scale2d 
  "Returns a function making use of a closure to
   perform a repeatable 2d scale of a coordinate pair."
  [sx sy]
  (fn [x y] [(* x sx) (* y sy)]))

(defn compose-transform
  "Create a composition function that returns a function that 
   combines two functions to perform a repeatable transformation."
  [f g]
  (fn [x y] (apply g (f x y))))

(defn memoize-transform
  "Returns a function that memoizes the last result.
   If the arguments are the same as the last call,
   the memoized result is returned."
  [f]
  (let [last-seen-coords (atom [0 0])
        last-result (atom [0 0])]
    (fn [x y]
      ;; If x and y are equal to last-seen-coords, return last-result.
      ;; Otherwise, compute the new result and save it to last-* vars and return
      ;; the newly stored result.
      (if (= [x y] @last-seen-coords)
        @last-result
        (do
          (reset! last-seen-coords [x y])
          (reset! last-result (f x y)))))))
