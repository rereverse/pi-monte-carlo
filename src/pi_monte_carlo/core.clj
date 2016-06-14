(ns pi-monte-carlo.core
  (:require [quil.core :as q]))

(def radius 0.5)

(defn sqr [x]
  (* x x))

(defn random-x-y []
  [(- (rand) radius) (- (rand) radius)])

(defn in-circle? [[x y]]
  (<= (+ (sqr x) (sqr y)) (sqr radius)))

;; 4 * Drops-in-cricle / All Drops
(defn pi [as ac]
  (double (/ (* 4 ac) as)))

(defn approx-pi [n-drop]
  (let [drops-in-cirle (->> (repeatedly n-drop #( in-circle? (random-x-y)))
                            (filter identity)
                            (count))]
    (pi n-drop drops-in-cirle)))

(def approx-pi-seq
  (->> (repeatedly #(in-circle? (random-x-y)))
       (reductions
         (fn [sum in-circ?]
           (if in-circ?
             (inc sum) sum))
         0)
       (map vector (drop 1 (range)))                                  ;; zip with 1, 2, 3, 4, ...
       (map #(apply pi %))))

(defn setup []
  (q/frame-rate 15)
  (q/background 0))

;; hacky state - dont need this really
(def ac (volatile! 0))
(def as (volatile! 0))

(defn draw-point []
  (vswap! as inc)
  (let [[x y] (random-x-y)]
    (if (in-circle? [x y])
      (do (q/stroke 255)
          (vswap! ac inc))
      (q/stroke 255 0 0))
    (q/point (* 600 (+ radius x)) (* 600 (+ radius y)))))

(defn draw []
  (doall (repeatedly 100 draw-point))
  (println (str "4*" @ac "/" @as "=") (pi @as @ac)))

(defn -main []
  (vreset! ac 0)
  (vreset! as 0)
  (q/defsketch MonteCarloPI
               :title "Estimate PI Monte Carlo Simluation"
               :settings #(q/smooth 2)
               :setup setup
               :draw draw
               :size [600 600]))
