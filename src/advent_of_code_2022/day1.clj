(ns advent-of-code-2022.day1
  (:require [advent-of-code-2022.core :as core]))


(def input-file "resources/2022-day1.txt")


(defn sum-seq [seq]
  (reduce + (map bigdec seq)))


(defn part1 []
  (->> input-file
       (core/load-split) ;; load file and split by line
       (partition-by #(= "" %)) ;; group on each empty line
       (filter #(not= '("") %)) ;; remove empty lines
       (map sum-seq) ;; map each list of numbers to it's sum
       (reduce max))) ;; find the max


(defn part2 []
  (->> input-file
       (core/load-split) ;; load file and split by line
       (partition-by #(= "" %)) ;; group on each empty line
       (filter #(not= '("") %)) ;; remove empty lines
       (map sum-seq) ;; map each list of numbers to it's sum
       (sort >) ;; sort by size
       (take 3) ;; take 3 largest
       (reduce +))) ;; sum them