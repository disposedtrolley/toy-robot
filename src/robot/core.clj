(ns robot.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str])
  (:gen-class))

(def directions
  ["north" "east" "south" "west"])

(defn hit-obstacle?
  [robot]
  (let [x (:x robot)
        y (:y robot)]
    (or (< x 0) (> x 4) (< y 0) (> y 4))))

(defn init-robot
  []
  {:x nil
   :y nil
   :f nil})

(defn placed?
  [robot]
  (every? #(not (nil? %)) [(:x robot) (:y robot) (:f robot)]))

(defn place
  [robot new-position]
  (let [new-x (Integer/parseInt (nth new-position 0))
        new-y (Integer/parseInt (nth new-position 1))
        new-f (nth new-position 2)
        candidate-robot (assoc robot :x new-x
                                     :y new-y
                                     :f new-f)]
    (cond
      (and (not (hit-obstacle? candidate-robot)) (some #{new-f} directions)) candidate-robot
      :else robot)))

(defn move
  [robot]
  (cond
    (placed? robot) (let [f (:f robot)
                          next-move (cond (= "north" f) [:y 1]
                                          (= "east" f) [:x 1]
                                          (= "south" f) [:y -1]
                                          (= "west" f) [:x -1])
                          axis (nth next-move 0)
                          step (nth next-move 1)
                          candidate-robot (assoc robot axis (+ (axis robot) step))]
                      (if  (hit-obstacle? candidate-robot) robot candidate-robot))
    :else robot))

(defn left
 [robot]
 (cond
   (placed? robot) (let [f (:f robot)]
                     (assoc robot :f
                       (cond (= (first directions) f) (last directions)
                             :else (nth directions (- 1 (.indexOf directions f))))))
   :else robot))

(defn right
  [robot]
  (cond
    (placed? robot) (let [f (:f robot)]
                      (assoc robot :f
                        (cond (= (last directions) f) (first directions)
                              :else (nth directions (+ 1 (.indexOf directions f))))))
    :else robot))

(defn report
  [robot]
  (if (placed? robot) (println (format "[Position] x: %d y: %d f: %s" (:x robot) (:y robot) (:f robot))))
  robot)

(defn -main
  [& args]
  (loop [robot (init-robot)]
    (let [next-instruction (mapv str/lower-case
                                (str/split (read-line) #" "))
          next-args (doall (drop 1 next-instruction))]
      (match next-instruction
             ["place" _ _ _] (recur (place robot next-args))
             ["move"] (recur (move robot))
             ["left"] (recur (left robot))
             ["right"] (recur (right robot))
             ["report"] (recur (report robot))
             :else (println "Invalid move!")))))