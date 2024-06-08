(ns grains)

(defn square [n]
  (->> (dec n)
       (nth (iterate #(* 2 %)
                     (bigint 1)))))

(defn total []
  (->> (range 1 65)
       (map square)
       (apply +)))
