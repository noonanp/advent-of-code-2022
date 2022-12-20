(ns advent-of-code-2022.day10
  (:require [advent-of-code-2022.core :as core]
            [clojure.string :as str]))


(def input-file "resources/2022-day10.txt")


(defn pad-addx [cmd]
  "if the command is an addx, add a noop before the addx to replicate the first cycle an add operation takes"
  (if (= cmd "noop")
    ["noop"]
    ["noop" cmd]))


(defn convert-to-x [cmd]
  "converts each command to the amount to be added, noop converts to 0, addx converts to the x value"
  (if (= cmd "noop")
    0
    (Integer/valueOf (second (str/split cmd #" ")))))


(defn apply-cmds [cmds]
  "Apply the commands and return a vector of the value of X at each cycle, index is 0 based so index is cycle - 1"
  (reduce
    #(conj %1 (+ (last %1) %2)) ;add the current value to the previous one and put it in the result vector
    [1] ; start the vector with X = 1
    cmds))


(defn running-values [input-file]
  "take the input file and converts the commands a vector of the values of X at each cycle"
  (->> input-file
       (core/load-split)
       (map pad-addx)
       (flatten)
       (map convert-to-x)
       (apply-cmds)))


(defn part1 []
  (let [x-vals (running-values input-file)]
    (reduce + [(* 20 (get x-vals 19))
               (* 60 (get x-vals 59))
               (* 100 (get x-vals 99))
               (* 140 (get x-vals 139))
               (* 180 (get x-vals 179))
               (* 220 (get x-vals 219))])))

(defn draw-char [[crt-pos x]]
  (if (< (Math/abs (- crt-pos x)) 2)
    \#
    \ ))


(defn part2 []
  (->> input-file
       (running-values)
       (interleave (cycle (range 40))) ;interleave the x values with 0-39 repeatedly
       (partition 2) ;partition the seq into (cycle, value) pairs
       (map draw-char)
       (partition 40)
       (map #(reduce str %))))




