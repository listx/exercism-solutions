(ns log-levels
  (:require [clojure.string :as str]))

(defn message
  "Takes a string representing a log line
   and returns its message with whitespace trimmed."
  [s]
  (let [parts (str/split s #"^\[(INFO|WARNING|ERROR)\]:\s+")]
    (if (= 2 (count parts))
      (str/trimr (nth parts 1))
      s)))

(defn log-level
  "Takes a string representing a log line
   and returns its level in lower-case."
  [s]
  ; Unlike `str/split`, `re-matches` will match against the entire string. So we
  ; need ".*" at the end here to allow matching any string as long as it has the
  ; log level prefix.
  ;
  ; The leading `(?s)` is to enable dotall mode to allow running the pattern
  ; against inputs that contain newlines.
  (let [pat #"(?s)^\[(INFO|WARNING|ERROR)\]:\s+.*"
        matches (re-matches pat s)]
    (if (= 2 (count matches))
      (-> (nth matches 1) .toLowerCase)
      "")))

(defn reformat
  "Takes a string representing a log line and formats it
   with the message first and the log level in parentheses."
  [s]
  (let [log-level (log-level s)
        message (message s)]
    (format "%s (%s)" message log-level)))
