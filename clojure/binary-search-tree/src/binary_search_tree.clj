(ns binary-search-tree)

(def value :value)

(defn singleton [n] {:value n})

;; Forward-declare `insert` so that we can use it in `singleton-or-follow`. If
;; we don't use a forward declaration we would be stuck (forever) trying to
;; unwrangle a circular dependency.
(declare insert)

;; Insert a value into an existing tree, or create a new tree. The "existing
;; tree" checks if `bst` is a node. A node must have a `:value` key, so that's
;; the check.
(defn singleton-or-follow [bst n]
  (if (:value bst)
    (insert n bst)
    (singleton n)))

;; The {:keys [...]} is discussed in https://clojure.org/guides/destructuring.
;; But basically given `{:keys [a b c]}` we bind the values for the keys :a, :b,
;; and :c to `a`, `b` and `c`.
(defn insert [n {:keys [value] :as bst}]
  (case (compare n value)
    ;; Grow the hashmap by creating a new root node with the same value.
    0  {:value n :left bst}
    ;; Descend one level by going into the value of for the key :left. If it is
    ;; another non-leaf node, it will have a `:value` key. The call to
    ;; `single-or-follow` ends up as `(single-or-follow (:left bst) n)` because
    ;; the old value is supplied as the first argument.
    -1 (update bst :left  singleton-or-follow n)
    ;; Same as above, but for the `:right` key.
    1  (update bst :right singleton-or-follow n)))

(def left :left)

(def right :right)

;; Recurse into the `bst` structure, but keep the current node's `value` in the
;; middle.
(defn to-list [{:keys [value left right] :as bst}]
  (if-not bst
    []
    (concat (to-list left) [value] (to-list right))))


(defn from-list [[fst & rst]]
  (reduce
   #(insert %2 %1)
   ;; Create initial bst from the first element.
   (singleton fst)
   ;; Process remaining elements.
   rst))
