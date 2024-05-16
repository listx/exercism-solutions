(ns etl
  (:require [clojure.string :as str]))

(defn transform
  "Convert a map of score-to-uppercase-letters to a map of
  lowercase-letters-to-score entries."
  [source]
  (->> (seq source)
       (map (fn [[score keys]]
              (zipmap
               (map str/lower-case keys)
               (repeat score))))
       (apply merge)))
