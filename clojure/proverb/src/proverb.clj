(ns proverb
  (:require [clojure.string :as str]))

;; Given a list of inputs, generate the relevant proverb. For example, given the
;; list ["nail", "shoe", "horse", "rider", "message", "battle", "kingdom"], you
;; will output the full text of this proverbial rhyme:
;;
;; For want of a nail the shoe was lost.
;; For want of a shoe the horse was lost.
;; For want of a horse the rider was lost.
;; For want of a rider the message was lost.
;; For want of a message the battle was lost.
;; For want of a battle the kingdom was lost.
;; And all for the want of a nail.

(defn leading-verse [[a b]]
  (format "For want of a %s the %s was lost." a b))

(defn last-verse [w]
  (format "And all for the want of a %s." w))

(defn recite
  [[fst & rst :as words]]
  (cond
    (nil? fst) ""
    (nil? rst) (last-verse fst)
    :else (->> words
               (partition 2 1)
               (mapv leading-verse)
               (#(conj % (last-verse fst)))
               (str/join "\n"))))
