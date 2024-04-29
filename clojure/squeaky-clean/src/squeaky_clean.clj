(ns squeaky-clean
  (:require [clojure.string :as str]))

(defn clean-whitespace
  [s]
  (str/replace s #"\s" "_"))

(defn clean-ctrl-chars
  [s]
  (str/replace s #"\p{Cc}" "CTRL"))

(defn clean-snake
  [s]
  (str/replace s #"-(\p{L})" #(str/upper-case (%1 1))))

(defn clean-non-word-char
  "Preserves underscores, but removes other non-word characters."
  [s]
  (str/replace s #"[^\p{L}]" #(if (= %1 "_") "_" "")))

(defn clean-greek-lowercase
  [s]
  (str/replace s #"[α-ω]" ""))

(defn clean
  "Cleans up identifier names."
  [s]
  (-> s
      clean-whitespace
      clean-ctrl-chars
      clean-snake
      clean-non-word-char
      clean-greek-lowercase))
