(ns beer-song
  (:require [clojure.string :refer [join]]))

(defn verse
  "Returns the nth verse of the song."
  [n]

  (cond
    (= 0 n) (str         "No more bottles of beer on the wall, no more bottles of beer.\n"
                         "Go to the store and buy some more, 99 bottles of beer on the wall.\n")
    (= 1 n) (str (format "%d bottle of beer on the wall, %d bottle of beer.\n" n n)
                         "Take it down and pass it around, no more bottles of beer on the wall.\n")
    (= 2 n) (str (format "%d bottles of beer on the wall, %d bottles of beer.\n" n n)
                         "Take one down and pass it around, 1 bottle of beer on the wall.\n")
    :else   (str (format "%d bottles of beer on the wall, %d bottles of beer.\n" n n)
                 (format "Take one down and pass it around, %d bottles of beer on the wall.\n" (dec n)))))

(defn sing
  "Given a start and an optional end, returns all verses in this interval. If
  end is not given, the whole song from start is sung."
  ([n]   (sing n 0))
  ([n k] (->> (range n (dec k) -1)
              (map verse)
              (join "\n"))))
