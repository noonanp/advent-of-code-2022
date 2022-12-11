(ns advent-of-code-2022.day5
  (:require [advent-of-code-2022.core :as core]
            [clojure.string :as str]))


(def input-file "resources/2022-day5.txt")

;;this is so hacky
(def stack-offsets [1 5 9 13 17 21 25 29 33])


(defn init-stack [strs pos]
  (->> strs
       (map  #(get % pos))
       (filter #(not= \space %))
       (filter #(not (nil? %)))
       vec))


(defn parse-move [n]
  (as-> n val
      (str/split val #" ")
      (map read-string val)
      (filter number? val)
      {:count (first val)
       :from (second val)
       :to (nth val 2)}))


(defn parse-moves [n]
  (->> n
       (drop 10)
       (map parse-move)))


(defn move-crate [from to stacks]
  (let [stacks (vec stacks)
        char-to-move (last (get stacks from))
        char-dropped (update stacks from drop-last)]
    (update char-dropped to #(concat % [char-to-move]))))


(defn apply-move [stacks move]
  (let [from (- (:from move) 1)
        to (- (:to move) 1)
        count (:count move)
        partial-move (partial move-crate from to)]
    (nth (iterate partial-move stacks) count)))


(defn part1 []
  (let [input (core/load-split input-file)
        stack-strs (reverse (take 8 input))
        stacks (map #(init-stack stack-strs %) stack-offsets)
        moves (parse-moves input)
        result (reduce apply-move (vec stacks) moves)]
    (reduce
      str
      (map last result))))


;;;; PART 2

(defn move-group [stacks move]
  (let [from (- (:from move) 1)
        to (- (:to move) 1)
        count (:count move)
        to-move (take-last count (get stacks from))
        removed (update stacks from #(drop-last count %))]
    (update removed to #(concat % to-move))))


(defn part2 []
  (let [input (core/load-split input-file)
        stack-strs (reverse (take 8 input))
        stacks (map #(init-stack stack-strs %) stack-offsets)
        moves (parse-moves input)
        result (reduce move-group (vec stacks) moves)]
    (reduce
      str
      (map last result))))




