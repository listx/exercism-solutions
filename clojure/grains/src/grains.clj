(ns grains)

(defn square [n]
  (.shiftLeft
   (biginteger 1)
   (dec n)))

(defn total []
  (->> (range 1 65)
       (map square)
       (apply +)))
