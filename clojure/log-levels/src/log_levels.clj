(ns log-levels
  (:require [clojure.string :as str]))

(def log-line-regex
  "The key to this regex is that it has 2 groups defined."
  #"^\[(INFO|WARNING|ERROR)\]:\s+(.*)")

(defn- parse-log-line
  "Parse a log line into parts."
  [s]
  (some->> s
           (re-find log-line-regex)
           ;; Drop the overall match; we only want to use the groups.
           (drop 1)))

(defn message
  "Takes a string representing a log line
   and returns its message with whitespace trimmed."
  [s]
  (-> s parse-log-line second str/trimr))
(defn log-level
  "Takes a string representing a log line
   and returns its level in lower-case."
  [s]
  (-> s parse-log-line first str/lower-case))
(defn reformat
  "Takes a string representing a log line and formats it
   with the message first and the log level in parentheses."
  [s]
  (format "%s (%s)"
          (message s)
          (log-level s)))
