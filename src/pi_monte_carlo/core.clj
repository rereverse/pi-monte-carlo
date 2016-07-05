(ns pi-monte-carlo.core
  (:require [quil.core :as q]
            [quil.middleware :as qm])
  (:import [java.util Random]))

(def radius 0.5)

(defn random-xys
  [seed]
  (let [r (new Random seed)
        rxy #(- (.nextDouble r) radius)]
    (repeatedly #(vector (rxy) (rxy)))))

(defn in-circle?
  [[x y]]
  (<= (+ (q/sq x)
         (q/sq y))
      (q/sq radius)))

(defn approx-pi
  [drops-square
   drops-circle]
  (-> 4.0
      (* drops-circle)
      (/ drops-square)))

(defn mc-approx-pi [n-experims seed]
  (let [drops-circle (->> (random-xys seed)
                          (take n-experims)
                          (map in-circle?)
                          (filter identity)
                          (count))]
    (approx-pi n-experims
               drops-circle)))

(defn approx-pi-seq [seed]
  (->> (random-xys seed)
       (map in-circle?)
       (reductions #(if %2 (inc %1) %1)
                   0)
       (map vector
            (rest (range)))
       (map #(apply approx-pi %))))

(defn setup []
  (q/background 0)
  (q/frame-rate 30)
  (let [seed (System/currentTimeMillis)
        xys (random-xys seed)
        in-circs (map in-circle? xys)
        ps (approx-pi-seq seed)]
    [xys in-circs ps]))

(defn update-state [s]
  (map rest s))

(defn draw [[[[x y]] [in-circ] [p]]]
  (q/with-stroke
    (if in-circ [255 0 0] [255])
    (q/point (* 600 (+ x radius))
             (* 600 (+ y radius))))
  (println "PI ~~" p))

(defn -main []
  (q/defsketch MonteCarloPI
               :title "Estimate PI Monte Carlo Simluation"
               :settings #(q/smooth 2)
               :setup setup
               :draw draw
               :update update-state
               :size [600 600]
               :middleware [qm/fun-mode]))
