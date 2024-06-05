(ns run-length-encoding)

(defn encode
  [[fst & rst :as entire]]
  ;; For "xxx", "rst" is "xx" so it is truthy (not nil).
  (cond->> fst
    rst (str (count entire))))

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (->> plain-text
       (partition-by identity)
       (map encode)
       (apply str)))

(defn decode
  [[_ n ltr]]
  (cond->> ltr
    n (repeat (Integer/parseInt n))))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [cipher-text]
  (->> cipher-text
       ;; Example: user> (re-seq #"(\d+)?(\D)" "3a2bc")
                   ;; => (["3a" "3" "a"] ["2b" "2" "b"] ["c" nil "c"])
       (re-seq #"(\d+)?(\D)")
       (mapcat decode)
       (apply str)))
