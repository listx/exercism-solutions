(ns spiral-matrix)

(defn walk-spiral
  "Walk a spiral around a matrix until we run out of room (until we're done)."
  [m [y x]]
  (let [north [(dec y) x]
        south [(inc y) x]
        east  [y (inc x)]
        west  [y (dec x)]
        at-north (get-in m north 1)
        at-south (get-in m south 1)
        at-east  (get-in m east 1)
        at-west  (get-in m west 1)]
    ;; If we've finished walking (no adjacent cell is unvisited), return the
    ;; matrix as is.
    (if (every? pos? [at-north at-south at-east at-west])
      m
      ;; Otherwise recurse to mutate it, building up our spiral.
      (let [dir (cond
                  (and (zero? at-east)
                       ;; Only go east if the cell above us is iether out of
                       ;; bounds (1) or already occupied by another number
                       ;; (positive).
                       (pos? at-north)) east
                  (zero? at-south) south
                  (zero? at-west) west
                  :else north)]
        (recur (assoc-in m
                         dir
                         (inc (get-in m [y x])))
               dir)))))

(defn spiral [n]
  (case n
    0 ()
    (->> (for [row (repeat n 0)] (vec (repeat n row)))
         (into [])
         ;; Set top-left corner to 1.
         (#(assoc-in % [0 0] 1))
         (#(walk-spiral % [0 0])))))
