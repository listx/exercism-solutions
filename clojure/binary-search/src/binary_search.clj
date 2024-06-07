(ns binary-search)

(defn middle-idx [haystack]
  (quot (count haystack) 2))

(defn search-for
  ([needle haystack] (search-for needle haystack 0))
  ([needle haystack offset]
   (let [midx (middle-idx haystack)
         [lft [candidate & rgt]] (split-at midx haystack)]

     (assert (seq haystack) (str "Element " needle " not found"))

     (case (compare needle candidate)
       0  (+ offset midx)
       -1 (recur needle lft offset)
       1  (recur needle rgt (+ offset midx 1))))))
