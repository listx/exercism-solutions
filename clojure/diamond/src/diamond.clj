(ns diamond)

(defn diamond [c]
  (let [offset (->> \A
                    int
                    (- (int c)))
        ;; Create substrings that are the "right" half of lines. E.g., for \C,
        ;; this would be
        ;;
        ;;  ["A  "
        ;;   " B "
        ;;   "  C"]
        substrs (for [x (range (inc offset))]
                  (str (->> (repeat x \space)
                            (apply str))
                       (->> (+ x (int \A))
                            char)
                       (->> (repeat (- offset x) \space)
                            (apply str))))
        ;; Mirror the right part of each substr over to the left. So now it
        ;; would look like
        ;;
        ;;  ["  A  "
        ;;   " B B "
        ;;   "C   C"]
        lines (map #(str (->> (drop 1 %)
                              reverse
                              (apply str))
                         %)
                   substrs)
        ;; Repeat all but the last line and put it at the end, giving us the
        ;; final diamond shape like this
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
