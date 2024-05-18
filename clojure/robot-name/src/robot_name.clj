(ns robot-name)

(defn- rand-name
  "Generate a random name. Called enough times, this function will start
  repeating itself (generate values that have been previously generated)."
  []
  (let [rand-cap #(char (+ 65 (rand-int 26)))
        rand-digit #(rand-int 10)]
    ;; The `(juxt ...)` returns a new function, so we have to wrap it in another
    ;; pair of parentheses to call the function. This is why we have `((juxt
    ;; ...))`.
    (apply str ((juxt rand-cap
                      rand-cap
                      rand-digit
                      rand-digit
                      rand-digit)))))

;; Create a Ref with an initial value of an empty set. We will start adding to
;; this set to keep track of previously-generated names. The "^:private" just
;; marks this Ref as a private one (not meant to be used outside of this
;; namespace).
;;
;; The seen-names is global --- it keeps track of all robot names at any given
;; point in time. Each robot's name must be guaranteed to be unique (no two
;; robots at a given time can have the same name).
(def ^:private seen-names (ref #{}))

(defn- new-name
  "Makes a new name and also adds it to `seen-names`."
  []
  ;; `dosync` runs the expressions in a single transaction (STM). Any effects on
  ;; Refs will be atomic.
  (dosync
    ;; `repeatedly` calls rand-name infinitely, lazily. The call to `remove`
    ;; will return `false` for the first rand-name which has not been previously
    ;; seen before (inside `seen-names`). Finally, the pattern matching in
    ;; `[generated-name]` will take the first of these previously-unseen names.
    (let [[generated-name] (->> (repeatedly rand-name)
                                (remove @seen-names))]
      ;; Add generated name into seen-names.
      (alter seen-names conj generated-name)
      generated-name)))

(defn robot
  "Make a new robot, by creating a new Ref for it containing a map. A robot
  can change its name when it is factory-reset, at which point it will assume a
  new name. We will store its name in a :name key in the map. Presumably as this
  codebase grows we can store other properties of the robot as new keys inside
  this map."
  []
  (ref {}))

(defn robot-name
  "Gives the current name of the robot. It's the value of the `:name` key in the
  map."
  [robot]
  ;; If the robot already has a name, just dereference it.
  (or (:name @robot)
      ;; Otherwise, generate a new name.
      (dosync
        (let [generated-name (new-name)]
          ;; Insert the `:name` key with `generated-name` into the map Ref.
          (alter robot assoc :name generated-name)
          generated-name))))

(defn reset-name
  "Resets the name of a robot to a different random name which has never been
  seen before."
  [robot]
  (dosync
    ;; When we reset the name, remove it from the `seen-names` set. This way, we
    ;; can "garbage-collect" the name from `seen-names` so that it can be reused
    ;; again in the future (as long as no other robots are also using that same
    ;; name).
    (alter seen-names disj (robot-name robot))
    ;; Clear the robot's name. The next time `robot-name` is asked, it will
    ;; generate a new name at that time.
    (alter robot dissoc :name)))
