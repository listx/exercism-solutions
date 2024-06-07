(ns change)

(defmacro ex->nil
  "Executes body, catching any Exceptions and returning nil instead"
  [& body]
  `(try ~@body (catch Exception _#)))

(defn throw-when
  "If (pred x) is truthy, throws ex. Otherwise, returns x"
  [pred ex x]
  (when (pred x) (throw ex))
  x)

(def issue
  (memoize
   (fn [n coins]
     (when-not (zero? n)
       (->> (filter #(<= % n) coins)
            (keep #(ex->nil (cons % (issue (- n %) coins))))
            (throw-when empty? (IllegalArgumentException. "cannot change"))
            (apply min-key count)
            sort)))))
