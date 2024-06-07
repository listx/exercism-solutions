(ns binary-search)

(defn middle
  "Return the middle item from coll, as well as its index"
  [coll]
  (let [len (count coll)
        mid (quot len 2)]
      [(nth coll mid) mid]))

(defn search-for-helper [item mid-idx coll]
  (let [mid-item (nth coll mid-idx)]
    (cond
      (= mid-item item) mid-idx

      (or (zero? mid-idx) (> mid-idx (dec (count coll))))
        (throw (IllegalArgumentException. "Number not found."))

      (< item mid-item) (search-for-helper item (quot mid-idx 2) coll)

      (< mid-item item) (search-for-helper item (+ 1 mid-idx (quot (- (count coll) mid-idx) 2)) coll))))

(defn search-for
  "Return the index of the item from the collection."
  [item coll]
  (let [[_ mid-idx] (middle coll)]
    (search-for-helper item mid-idx coll)))
