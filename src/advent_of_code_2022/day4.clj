(ns advent-of-code-2022.day4
  (:require [advent-of-code-2022.core :as core]
            [clojure.string :as str]))


(def input-file "resources/2022-day4.txt")


(defn parse-ints [seq]
  (map #(Integer/parseInt %) seq))


(defn fully-contains? [[a b x y]]
  (or
    (and (<= a x) (>= b y)) ;; [a b] contains [x y]
    (and (>= a x) (<= b y)))) ;; [x y] contains [a b]


(defn part1 []
  (->> input-file
       (core/load-split)
       (map #(str/split % #"[-,]"))
       (map parse-ints)
       (filter fully-contains?)
       (count)))


(defn overlaps? [[a b x y]]
  (or
    (and (<= a x) (>= b x)) ;; x is between [a b]
    (and (<= a y) (>= b y)) ;; y is between [a b]
    (and (<= x a) (>= y a)) ;; a is between [x y]
    (and (<= x b) (>= y b)))) ;; b is between [x y]


(defn part2 []
  (->> input-file
       (core/load-split)
       (map #(str/split % #"[-,]"))
       (map parse-ints)
       (filter overlaps?)
       (count)))
