(ns gigasecond
  (:import [java.time LocalDateTime]))

(def extract-ymd
  (juxt
   #(.getYear %)
   #(.getMonthValue %)
   #(.getDayOfMonth %)))

(defn datetime-from [y m d]
  ;; See
  ;; https://docs.oracle.com/javase%2F8%2Fdocs%2Fapi%2F%2F/java/time/LocalDateTime.html#of-int-int-int-int-int-int-.
  (LocalDateTime/of y m d 0 0 0))

(defn from
  "Add 1 billion seconds from the given date."
  [y m d]
  (-> ;; Construct LocalDateTime object.
      (datetime-from y m d)
      ;; Add 1 billion seconds.
      (.plusSeconds (Math/pow 10 9))
      ;; Retrieve the LocalDate part of the (modified) LocalDateTime.
      (.toLocalDate)
      extract-ymd))
