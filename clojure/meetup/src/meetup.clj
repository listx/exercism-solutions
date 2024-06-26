(ns meetup
  (:import
    (java.time DayOfWeek YearMonth)))

;; Idea originally from
;; https://www.oreilly.com/library/view/clojure-cookbook/9781449366384/ch01.html,
;; which is a freely available page on "1.32. Generating Ranges of Dates and
;; Times Using Native Java Types by Tom Hicks" from the book "Clojure Cookbook:
;; Recipes for Functional Programming". But this version uses
;; java.time.YearMonth, not java.util.GregorianCalendar, becaure the latter is
;; legacy code according to
;; https://docs.oracle.com/javase/tutorial/datetime/iso/legacy.html.
(defn days-from-year-month [year month]
  (let [ym (YearMonth/of year month)
        month-len (.lengthOfMonth ym)]
    (->> (range 1 (inc month-len))
         (map #(. ym (atDay %))))))

(defn meetup
  "Return a date, as a list of (YEAR MONTH DAY) which corresponds with the
  desired month, year, day-name, and week-name. `day-name` is :monday :tuesday
  :wednesday, etc and `week-name` is one of `first`, `second`, `third`,
  `fourth`, `last`, and `teenth`."
  [month year day-name week-name]
  ;; First get all days of the month that have the given `day-name`. E.g. if
  ;; `day-name` is `:monday`, get all the dates that are on a Monday. Then just
  ;; figure out which one to pick based on the `week-name`.
  (let [day-name-as-int
        ({:sunday    DayOfWeek/SUNDAY
          :monday    DayOfWeek/MONDAY
          :tuesday   DayOfWeek/TUESDAY
          :wednesday DayOfWeek/WEDNESDAY
          :thursday  DayOfWeek/THURSDAY
          :friday    DayOfWeek/FRIDAY
          :saturday  DayOfWeek/SATURDAY} day-name)
        days (->> (days-from-year-month year month)
                  ;; Remove days that don't fall on the desired `day-name`.
                  (filter #(= day-name-as-int
                              (.getDayOfWeek %)))
                  (map #(.getDayOfMonth %)))]
    (conj [year month] (->> days
                            ((case week-name
                               :teenth #(some #{13 14 15 16 17 18 19} %)
                               :last last
                               :first first
                               :second second
                               :third #(nth % 2)
                               :fourth #(nth % 3)))))))
