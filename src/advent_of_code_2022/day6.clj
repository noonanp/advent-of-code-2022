(ns advent-of-code-2022.day6
  (:require [advent-of-code-2022.core :as core]))


(def input-file "resources/2022-day6.txt")


(defn distinct-seq? [seq]
  (= (count seq) (count (distinct seq)))) ;; compare the size of the seq to the size of the distinct seq


(defn create-partitions [n input-file]
  "Create a vector of partitions, each partition is of n characters"
  (->> input-file
       (core/load-split)
       (first)
       (partition n 1)
       (vec)))


(defn part1 []
  (let [partitions (create-partitions 4 input-file)
        first-unique (first (filter distinct-seq? partitions))]
    (+ 4 (.indexOf partitions first-unique)))) ;; index of the first unique 4 char group + 4 as the index is the count
                                               ;; to the beginning of the marker, and the answer is the end of the marker


(defn part2 []
  (let [partitions (create-partitions 14 input-file)
        first-unique (first (filter distinct-seq? partitions))]
    (+ 14 (.indexOf partitions first-unique)))) ;; index of the first unique 14 char group + 14 as the index is the count
                                                ;; to the beginning of the marker, and the answer is the end of the marker





