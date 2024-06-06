(ns strain)

(defn retain [p arr]
  (loop [[fst & rst :as coll] arr
         result []]
    (cond
      (empty? coll) result
      (p fst)       (recur rst (conj result fst))
      :else         (recur rst result))))

(defn discard [p arr]
  (retain (complement p) arr))
