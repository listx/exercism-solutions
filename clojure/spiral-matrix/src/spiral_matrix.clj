(ns spiral-matrix)

(defn at-pos
  "Get the value at the given position [y x] from matrix `m`. If out of bounds,
  return -1."
  [[y x] m]
  (try (aget m y x)
       (catch ArrayIndexOutOfBoundsException _ -1)))

(defn walk-spiral
  "Walk a spiral around a matrix until we run out of room (until we're done)."
  [[m [y x]]]
  (let [old-val (aget m y x)
        north [(dec y) x]
        south [(inc y) x]
        east  [y (inc x)]
        west  [y (dec x)]
        at-north (at-pos north m)
        at-south (at-pos south m)
        at-east  (at-pos east m)
        at-west  (at-pos west m)]
    ;; If we've finished walking (no adjacent cell is unvisited), return the
    ;; matrix as is.
    (if (every? #(not (zero? %)) [at-north at-south at-east at-west])
      m
      ;; Otherwise recurse to mutate it, building up our spiral.
      (let [[y-new x-new :as dir]
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
        (aset m y-new x-new (inc old-val))
        ;; Now that `m` is mutated, return it along with the direction `dir` we
        ;; used.
        (walk-spiral [m dir])))))

(defn spiral [n]
  (case n
    0 ()
    (let [m (->> (for [row (repeat n 0)] (repeat n row))
                 to-array-2d)]
      ;; Set top-left corner to 1.
      (aset m 0 0 1)
      (->> (walk-spiral [m [0 0]])
           ;; Visually this looks correct at this point like [[...]] (probably
           ;; because of our use of `to-array-2d`), but the tests require lists,
           ;; not vectors, so we have to convert it as such.
           (map #(apply list %))
           (apply list)))))
