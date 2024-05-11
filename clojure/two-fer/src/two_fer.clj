(ns two-fer)

(defn response [name]
  (format "One for %s, one for me." name))

(defn two-fer
  ([]      (response "you"))
  ([name]  (response name)))
