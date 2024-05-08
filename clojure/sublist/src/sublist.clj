(ns sublist)

(defn sublist?
  "True if list1 is a sublist of list2."
  [list1 list2]
  (not= -1 (java.util.Collections/indexOfSubList list2 list1)))

(defn classify [list1 list2]
  (cond
    (= list1 list2) :equal
    (sublist? list1 list2) :sublist
    (sublist? list2 list1) :superlist
    :else :unequal))
