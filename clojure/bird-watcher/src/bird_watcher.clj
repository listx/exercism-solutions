(ns bird-watcher)

(def last-week
  [0 2 5 3 7 8 4])

(defn today [birds]
  (last birds))

(defn inc-bird [birds]
  (assoc birds
         (- (count birds) 1)
         (+ 1 (last birds))))

(defn day-without-birds? [birds]
  (let [birdless-day (some #(= 0 %) birds)]
    ; In Clojure, `nil` is not falsy (see `(false? nil)` which
    ; returns false). So we have to return `false` explicitly.
    (if birdless-day true false)))

(defn n-days-count [birds n]
  (let [subarray (subvec birds 0 n)]
    (reduce #(+ %1 %2) 0 subarray)))

(defn busy-days [birds]
    (reduce #(+ %1 (if (>= %2 5) 1 0)) 0 birds))

(defn odd-week? [birds]
  (let [even-idx-counts (take-nth 2 (drop 1 birds))
        odd-idx-counts (take-nth 2 birds)]
    (and (every? #(= 0 %) even-idx-counts)
         (every? #(= 1 %) odd-idx-counts))))
