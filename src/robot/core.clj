(ns robot.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str])
  (:gen-class))

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
  (while true
    (let [next-instruction (mapv str/lower-case
                                (str/split (read-line) #" "))
          next-args (drop next-instruction)]
      (println (format "next-instruction: %s\n" next-instruction))
      (match next-instruction
             ["place" _ _ _] (place :r next-args)
             ["move"] (move :r)
             ["left"] (left :r)
             ["right"] (right :r)
             ["report"] (report :r)
             :else (println "Invalid move!")))))