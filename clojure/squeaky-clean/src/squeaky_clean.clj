(ns squeaky-clean
  (:require [clojure.string :as str]))

(defn clean-whitespace
  [s]
  (str/replace s #"\s" "_"))

(defn clean-ctrl-chars
  [s]
  (str/replace s #"[\u0000-\u001F]|[\u007f-\u009f]" "CTRL"))

(defn clean-snake
  [s]
  (str/replace s #"-(\S)" #(.toUpperCase (%1 1))))

(defn clean-non-word-char
  [s]
  (str/replace s #"[^\p{IsAlphabetic}]" #(if (= %1 "_") "_" "")))

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
