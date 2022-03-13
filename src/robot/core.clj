(ns robot.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str])
  (:gen-class))

(def directions
  [:north :south :east :west])

(defn init-robot
  []
  {:position nil
   :direction nil})

(defn place
  [robot position]
  (println "place..."))

(defn move
  [robot]
  (println "move..."))

(defn left
  [robot]
  (println "left..."))

(defn right
  [robot]
  (println "right..."))

(defn report
  [robot]
  (println "report..."))

(defn -main
  [& args]
  (loop [robot (init-robot)]
    (let [next-instruction (mapv str/lower-case
                                (str/split (read-line) #" "))
          next-args (drop next-instruction)]
      (println (format "next-instruction: %s\n" next-instruction))
      (match next-instruction
             ["place" _ _ _] (recur (place :r next-args))
             ["move"] (recur (move :r))
             ["left"] (recur (left :r))
             ["right"] (recur (right :r))
             ["report"] (recur (report :r))
             :else (println "Invalid move!")))))