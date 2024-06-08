(ns grade-school)

(defn grade
  [db gr]
  ;; Return the empty vector is a non-existent key (grade) is fetched.
  (get db gr []))

(defn add
  [db name gr]
  ;; Return a new db (hashmap) with the `name` appended to the old value.
  (update db gr concat [name]))

(defn sorted
  [db]
  ;; `sorted-map` sorts a hashmap by its keys. Because the value are left as-is,
  ;; we have to call `(sort v)`.
  (into (sorted-map)
    ;; Note that [[k v] db] can range through all keys and values in `db`.
    (for [[k v] db] [k (sort v)])))
