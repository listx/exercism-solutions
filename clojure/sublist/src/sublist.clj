(ns sublist)

(defn sublists
  "Given a list, return a list of lists. Each item in the result
  list is of size 'sublist-size', and is spaced apart 1 element
  from the other lists. For example, (sublists 2 [1 2 3])
  returns '((1 2) (2 3))."
  [list sublist-size]
  (partition sublist-size 1 list))

(defn sublist?
  "True if list1 is a sublist of list2."
  [list1 list2]
  (some (partial = list1)
        (sublists list2 (count list1))))

(defn classify [list1 list2]
  (cond
    (= list1 list2) :equal
    (sublist? list1 list2) :sublist
    (sublist? list2 list1) :superlist
    :else :unequal))
