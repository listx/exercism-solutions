(ns diamond)

(defn diamond [c]
  (let [
        ;; Create helper function.
        join (partial apply str)

        offset (->> \A
                    int
                    (- (int c)))

        ;; Create substrings that are the "right" half of lines. E.g., for \C,
        ;; this would be
        ;;
        ;;  ["A  "
        ;;   " B "
        ;;   "  C"]
        ;;
        ;; Do this by creating a string "   " and then place the A, B and C into
        ;; position in the correct index.
        substrs (for [i (range (inc offset))]
                  (->> (repeat (inc offset) \space)
                       vec
                       (#(assoc-in % [i] (->> (+ i (int \A))
                                              char)))
                       join))

        ;; Mirror the right part of each substr over to the left. So now it
        ;; would look like
        ;;
        ;;  ["  A  "
        ;;   " B B "
        ;;   "C   C"]
        lines (map #(str (->> (drop 1 %)
                              reverse
                              join)
                         %)
                   substrs)

        ;; Mirror the lines up top (all but the last) and put it at the end,
        ;; giving us the final diamond shape like this
        ;;
        ;;  ["  A  "
        ;;   " B B "
        ;;   "C   C"
        ;;   " B B "
        ;;   "  A  "]
        final (->> (concat lines
                           (->> lines
                                (take offset)
                                reverse))
                   vec)]
    final))
