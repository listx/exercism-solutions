(ns reverse-string)

(defn reverse-string [s]
  (->> s
       (apply conj ())
       (apply str)))
