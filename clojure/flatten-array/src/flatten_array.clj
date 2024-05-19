(ns flatten-array)

(defn flatten
  "Here the code is stolen from the code behind clojure.core/flatten. All of the
  hard work is done by tree-seq, which does a depth-first walk of the given
  collection. Each time we go down a level of nesting (when we encounter another
  sequential collection with `sequential?`) we iterate inside it by calling
  `seq`. Then we just remove all nils and sequential items from the walk."
  [coll]
  (->> (tree-seq sequential? seq coll)
       (remove #(or (nil? %) (sequential? %)))))
