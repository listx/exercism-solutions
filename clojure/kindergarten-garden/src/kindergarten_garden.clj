(ns kindergarten-garden
  (:require [clojure.string :as str]))

;; Create a list of lists, each list containing the 4 plants for each student.
(defn parse-plants [plants]
  (->> ;; => "CGRV\nCGRV"
       plants

       ;; => ["CGRV" "CGRV"]
       str/split-lines

       ;; Convert the letters into plant names.
       (map #(map {\C :clover
                   \G :grass
                   \R :radishes
                   \V :violets} %))

       ;; Note that this is a list of 2 lists now, one for each row. But we
       ;; would like to "pull out" the columns of each row and put them
       ;; together...
       ;;
       ;; => (((:clover :grass) (:radishes :violets)) ((:clover :grass) (:radishes :violets)))
       (map #(partition 2 %))

       ;; ... and that's what we do here. Pulling out the columns with mapv is a
       ;; common trick.
       (apply mapv concat)))

(defn normalize-students [students]
  (->> (sort students)
       (map (comp keyword str/lower-case))))

(defn garden
  ([plants] (garden plants
                    ["Alice"
                     "Bob"
                     "Charlie"
                     "David"
                     "Eve"
                     "Fred"
                     "Ginny"
                     "Harriet"
                     "Ileana"
                     "Joseph"
                     "Kincaid"
                     "Larry"]))
  ([plants students]
   (->> (parse-plants plants)
        (zipmap (normalize-students students)))))
