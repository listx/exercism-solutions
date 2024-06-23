(ns spiral-matrix)

(defn at-pos
  "Get the value at the given position [y x] from matrix `m`. If out of bounds,
  return -1."
  [[y x] m]
  (try (aget m y x)
       (catch ArrayIndexOutOfBoundsException _ -1)))

(defn next-cell
  "Return the next cell to visit in order to walk the given matrix as a spiral.
  Use the starting position [y x] as a reference. Also, look around the matrix
  itself to see which positions have not been walked yet, in order to determine
  which direction we should use."
  [[m [y x]]]
  (let [val   (aget m y x)
        north [(dec y) x]
        south [(inc y) x]
        east  [y (inc x)]
        west  [y (dec x)]
        at-north (at-pos north m)
        at-south (at-pos south m)
        at-east  (at-pos east m)
        at-west  (at-pos west m)
        [y-new x-new :as dir]
          (cond
            (and (zero? at-east)
                 ;; Only go east if the cell above us is iether out of bounds
                 ;; (-1) or already occupied by another number (positive).
                 (or (neg? at-north)
                     (pos? at-north))) east
            (zero? at-south) south
            (zero? at-west) west
            :else north)]
    ;; `aset` does not return the 2d array. It returns the value we used. So
    ;; here we invoke it to cause a side effect (the mutation of `m`).
    (aset m y-new x-new (inc val))
    ;; Now that `m` is mutated, return it along with the direction `dir` we
    ;; used.
    [m dir]))

(defn spiral [n]
  (case n
    0 ()
    (let [m (->> (for [row (repeat n 0)] (repeat n row))
                 to-array-2d)]
      ;; Set top-left corner to 1.
      (aset m 0 0 1)
      (->> (iterate next-cell [m [0 0]])
           (drop (dec (* n n)))
           first
           first
           ;; Visually this looks correct at this point like [[...]] (probably
           ;; because of our use of `to-array-2d`), but the tests require lists,
           ;; not vectors, so we have to convert it as such. Because list
           ;; construction reverses thinsgs, we have to un-reverse them back by
           ;; manually by calling `reverse` on both the y and x axes.
           (map #(into () %))
           (map reverse)
           (into ())
           reverse))))
