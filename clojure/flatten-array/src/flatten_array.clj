(ns flatten-array)

(defn flatten [arr]
  (loop [[fst & rst :as coll] arr
         result []]
    (cond
      ;; If we're done processing, return the result.
      (empty? coll)    result
      ;; If the element is a collection, unnest it one level and start over.
      (coll? fst)      (recur (concat fst rst) result)
      ;; If the element is nil, skip over it.
      (nil? fst)       (recur rst result)
      ;; If the element is not nil, save it to the result.
      :else            (recur rst (conj result fst)))))
