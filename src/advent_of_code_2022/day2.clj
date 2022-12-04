(ns advent-of-code-2022.day2
  (:require [advent-of-code-2022.core :as core]))

(def input-file "resources/2022-day2.txt")


(defn decode-me [me]
  (case me
    \X :rock
    \Y :paper
    \Z :scissors))


(defn decode-opp [opp]
  (case opp
    \A :rock
    \B :paper
    \C :scissors))


(defn score-result [res]
  (case res
    :lose 0
    :draw 3
    :win 6))


(defn calc-result [opp me]
  "Convert opponent choice and my choice to a result"
  (cond
    (= opp me) :draw
    (and (= me :rock) (= opp :scissors)) :win
    (and (= me :paper) (= opp :rock)) :win
    (and (= me :scissors) (= opp :paper)) :win
    :else :lose))


(defn score-choice [me]
  (case me
    :rock 1
    :paper 2
    :scissors 3))


(defn score1 [[opp _ me]]
  (let [me (decode-me me)
        opp (decode-opp opp)]
    (+ (score-choice me)
       (score-result (calc-result opp me)))))


(defn part1 []
  (->> input-file
       (core/load-split) ;; load file and split lines
       (map score1) ;; map each line/round to it's score
       (reduce +))) ;; sum all the lines


;;;;; PART 2

(defn decode-result [res]
  (case res
    \X :lose
    \Y :draw
    \Z :win))


(defn result-to-me [opp res]
  "Convert opponent choice and expected result to my choice"
  (cond
    (= res :draw) opp
    (and (= res :win) (= opp :rock)) :paper
    (and (= res :win) (= opp :paper)) :scissors
    (and (= res :win) (= opp :scissors)) :rock
    (and (= res :lose) (= opp :rock)) :scissors
    (and (= res :lose) (= opp :paper)) :rock
    (and (= res :lose) (= opp :scissors)) :paper))


(defn score2 [[opp _ res]]
  (let [opp (decode-opp opp)
        res (decode-result res)]
    (+ (score-choice (result-to-me opp res))
       (score-result res))))


(defn part2 []
  (->> input-file
       (core/load-split) ;; load file and split lines
       (map score2) ;; map each line/round to it's score
       (reduce +))) ;; sum all the lines)
