(ns space-age)

;; Mercury: orbital period 0.2408467 Earth years
;; Venus: orbital period 0.61519726 Earth years
;; Earth: orbital period 1.0 Earth years, 365.25 Earth days, or 31557600 seconds
;; Mars: orbital period 1.8808158 Earth years
;; Jupiter: orbital period 11.862615 Earth years
;; Saturn: orbital period 29.447498 Earth years
;; Uranus: orbital period 84.016846 Earth years
;; Neptune: orbital period 164.79132 Earth years


(defn on-earth [secs]
  (/ secs 31557600.0))

(defn on-mercury [secs]
  (-> (on-earth secs)
      (/ 0.2408467)))

(defn on-venus [secs]
  (-> (on-earth secs)
      (/ 0.61519726)))

(defn on-mars [secs]
  (-> (on-earth secs)
      (/ 1.8808158)))

(defn on-jupiter [secs]
  (-> (on-earth secs)
      (/ 11.862615)))

(defn on-saturn [secs]
  (-> (on-earth secs)
      (/ 29.447498)))

(defn on-uranus [secs]
  (-> (on-earth secs)
      (/ 84.016846)))

(defn on-neptune [secs]
  (-> (on-earth secs)
      (/ 164.79132)))
