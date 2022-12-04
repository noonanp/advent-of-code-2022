(ns advent-of-code-2022.day3
  (:require [advent-of-code-2022.core :as core]))


(def input-file "resources/2022-day3.txt")


(defn split-in-half [s]
  (let [n (/ (count s) 2)]
    (split-at n s)))


(defn find-incorrect-char [[x y]]
  (->> (concat (distinct x) (distinct y)) ;; join distinct chars from each half
       (frequencies) ;; get the frequencies of each character
       (filter #(= (val %) 2)) ;; find the only character with a freq=2
       (first) ;; first, as there is only one char in both sides
       (first))) ;; take the character


(defn priority [c]
  (if (Character/isUpperCase c) ;; check for case here as the priority is ordered lower -> upper, but ascii goes upper -> lower
    (- (int c) 38) ;; 38 is the offset required to convert an uppercase ASCII char to the correct priority
    (- (int c) 96))) ;; 96 is the offset required to convert an lowercase ASCII char to the correct priority


(defn part1 []
  (->> input-file
       (core/load-split)
       (map split-in-half)
       (map find-incorrect-char)
       (map priority)
       (reduce +)))


(defn find-common [[x y z]]
  (->> (concat (distinct x) (distinct y) (distinct z)) ;; join distinct chars from all three strings
       (frequencies) ;; get the frequencies of each character
       (filter #(= (val %) 3)) ;; find the only character in all three strings, with a freq=3
       (first) ;; first, as there is only one char in both sides
       (first))) ;; take the character


(defn part2 []
  (->> input-file
       (core/load-split)
       (partition 3)
       (map find-common)
       (map priority)
       (reduce +)))