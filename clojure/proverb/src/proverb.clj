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

(defn recite
  [[fst & _ :as words]]
  (if (empty? words)
    ""
    (let [mk-leading-verse (fn [[a b]]
                             (format "For want of a %s the %s was lost." a b))
          leading-verses (->> words
                              (partition 2 1)
                              (map mk-leading-verse)
                              (apply vector))
          last-verse (format "And all for the want of a %s." fst)]
      (->> leading-verses
           (#(conj % last-verse))
           (str/join "\n")))))
