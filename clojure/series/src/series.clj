(ns series)

(defn slices [string length]
  (if (zero? length)
    [""]
    (->> (partition length 1 string)
         (map #(apply str %)))))
