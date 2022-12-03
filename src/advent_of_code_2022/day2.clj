(ns advent-of-code-2022.day2
  (:require [advent-of-code-2022.core :as core]))

(def input-file "resources/2022-day2.txt")


(defn me-to-rps [me]
  (case me
    \X :rock
    \Y :paper
    \Z :scissors))


(defn opp-to-rps [opp]
  (case opp
    \A :rock
    \B :paper
    \C :scissors))


(defn score-result [opp me]
  (let [me (me-to-rps me)
        opp (opp-to-rps opp)]
    (cond
      (= opp me) 3
      (and (= me :rock) (= opp :scissors)) 6
      (and (= me :paper) (= opp :rock)) 6
      (and (= me :scissors) (= opp :paper)) 6
      :else 0)))


(defn score-choice [me]
  (let [me (me-to-rps me)]
    (case me
      :rock 1
      :paper 2
      :scissors 3)))


(defn score [[opp _ me]]
  (+ (score-choice me)
     (score-result opp me)))


(defn part1 []
  (->> input-file
       (core/load-split) ;; load file and split lines
       (map score) ;; map each line/round to it's score
       (reduce +))) ;; sum all the lines


(defn part2 [])
