(ns kindergarten-garden
  (:require [clojure.string :as str]))

;; Create a list containing 2 items, one for each row.
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

       ;; Note that this is a list of 2 lists now, one for each row. The key is
       ;; that the plants are grouped into pairs. The key idea here is to assign
       ;; one row correctly to each student, and to repeat this procedure again
       ;; for the second row. Then we can just merge the two results together to
       ;; get the combined answer where each student is responsible for both
       ;; rows.
       ;;
       ;; => (((:clover :grass) (:radishes :violets)) ((:clover :grass) (:radishes :violets)))
       (map #(partition 2 %))))

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
        (map #(zipmap (normalize-students students) %))
        (apply merge-with concat))))
