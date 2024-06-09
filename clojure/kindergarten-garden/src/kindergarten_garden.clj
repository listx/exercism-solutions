(ns kindergarten-garden
  (:require [clojure.string :as str]))

;; Transpose plants string into groups of 4 plants, with columnar ordering (not
;; the row-wise ordering as given in the input).
(defn parse-plants [plants]
  (->> ;; => "ABCD\nEFGH"
       plants

       ;; => ["ABCD" "EFGH"]
       str/split-lines

       ;; Note that this is a list of 2 lists now, one for each row.
       ;;
       ;; => (((\A \B) (\C \D)) ((\E \F) (\G \H)))
       (map #(partition 2 %))

       ;; We get the nth item from each of the 2 lists. The computed argument
       ;; for `nth` is the column number (where column 0 gets (\A \B) from row 1
       ;; and (\E \F) from row 2).
       ;;
       ;; => ((\A \B \E \F) (\C \D \G \H))
       ((fn [rows]
          (for [col (range (quot (dec (count plants)) 4))]
            (mapcat #(nth % col) rows))))

       ;; Convert the letters into plant names.
       (map #(replace {\C :clover
                       \G :grass
                       \R :radishes
                       \V :violets} %))))

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
   (->> students
        (map (comp keyword str/lower-case))
        sort
        (#(zipmap % (parse-plants plants))))))
