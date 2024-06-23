(ns meetup)

;; Adapted from
;; https://www.oreilly.com/library/view/clojure-cookbook/9781449366384/ch01.html,
;; which is a freely available page on "1.32. Generating Ranges of Dates and
;; Times Using Native Java Types by Tom Hicks" from the book "Clojure Cookbook:
;; Recipes for Functional Programming".
(defn days-from-year-month [year month]
  (let [start-date
        (java.util.GregorianCalendar.
         year
         ;; Java's MONTH is 0-based (0 for January).
         (dec month)
         ;; Start from the end of the previous month. This way, we can start the
         ;; series from the 1st of this month (instead of the 2nd).
         0)]
    (->> (repeatedly
          (fn []
            (.add start-date java.util.Calendar/DAY_OF_YEAR 1)
            ;; We can't just `.add` by itself because it returns nothing (it
            ;; relies on a side-effect). So that's why we `.clone` it here to
            ;; create a new object out of it.
            (.clone start-date)))
         ;; Only keep those dates that are inside the month.
         (take-while #(= (dec month)
                         (.get % java.util.Calendar/MONTH))))))

(defn meetup
  "Return a date, as a list of (YEAR MONTH DAY) which corresponds with the
  desired month, year, day-name, and week-name. `day-name` is :monday :tuesday
  :wednesday, etc and `week-name` is one of `first`, `second`, `third`,
  `fourth`, `last`, and `teenth`."
  [month year day-name week-name]
  ;; First get all days of the month that have the given `day-name`. E.g. if
  ;; `day-name` is `:monday`, get all the dates that are on a Monday. Then just
  ;; figure out which one to pick based on the `week-name`.
  (let [day-name-as-int ({:sunday 1
                          :monday 2
                          :tuesday 3
                          :wednesday 4
                          :thursday 5
                          :friday 6
                          :saturday 7} day-name)
        dates (->> (days-from-year-month year month)
                   ;; Remove days that don't fall on the desired `day-name`.
                   (filter #(= day-name-as-int
                               (.get % java.util.Calendar/DAY_OF_WEEK))))
        ;; Convert date to a simple 3-number vector [YEAR MONTH DAY].
        simple-date #(vector (.get % java.util.Calendar/YEAR)
                             (inc (.get % java.util.Calendar/MONTH))
                             (.get % java.util.Calendar/DAY_OF_MONTH))]
    (case week-name
      :teenth (->> dates
                   (some #(when (#{13 14 15 16 17 18 19}
                                 (.get % java.util.Calendar/DAY_OF_MONTH))
                            %))
                   simple-date)
      :last (->> dates
                 last
                 simple-date)
      (->> dates
           (drop ({:first 0
                   :second 1
                   :third 2
                   :fourth 3} week-name))
           first
           simple-date))))
