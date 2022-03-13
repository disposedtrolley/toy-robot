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
  (println "place...")
  (println new-position)
  (let [new-x (Integer/parseInt (nth new-position 0))
        new-y (Integer/parseInt (nth new-position 1))
        new-f (nth new-position 2)]
    (cond
      (some #{new-f} directions) (assoc robot :x new-x
                                              :y new-y
                                              :f new-f)
      :else robot)))

(defn move
  [robot]
  (println "move...")
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
 (println "left...")
 robot)

(defn right
  [robot]
  (println "right...")
  robot)

(defn report
  [robot]
  (println "report...")
  (println (format "[Position] x: %d y: %d f: %s" (:x robot) (:y robot) (:f robot)))
  robot)

(defn -main
  [& args]
  (loop [robot (init-robot)]
    (let [next-instruction (mapv str/lower-case
                                (str/split (read-line) #" "))
          next-args (doall (drop 1 next-instruction))]
      (println (format "next-instruction: %s\n" next-instruction))
      (match next-instruction
             ["place" _ _ _] (recur (place robot next-args))
             ["move"] (recur (move robot))
             ["left"] (recur (left robot))
             ["right"] (recur (right robot))
             ["report"] (recur (report robot))
             :else (println "Invalid move!")))))