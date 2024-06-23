(ns largest-series-product)

(def char-to-digit
  {\0 0N
   \1 1N
   \2 2N
   \3 3N
   \4 4N
   \5 5N
   \6 6N
   \7 7N
   \8 8N
   \9 9N})

(defn largest-product [n digits]
  (let [groups (partition n 1 digits)
        products (->> groups
                      (map #(map char-to-digit %))
                      (map #(apply * %)))]
  (apply max products)))
