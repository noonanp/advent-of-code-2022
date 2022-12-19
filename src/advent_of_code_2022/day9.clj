(ns advent-of-code-2022.day9
  (:require [advent-of-code-2022.core :as core]
            [clojure.string :as str]))


(def input-file "resources/2022-day9.txt")


(defn expand-direction [[dir count]]
  "Convert a number of moves in a direction to multiple single moves, eg U 4 -> U U U U"
  (repeat (Integer/valueOf count) dir))


(defn touching? [head tail]
  (let [xdiff (Math/abs (- (:x head) (:x tail)))
        ydiff (Math/abs (- (:y head) (:y tail)))]
    (and (<= xdiff 1)
         (<= ydiff 1))))


(defn move-head [head dir]
  (case dir
    "U" (update head :y inc)
    "D" (update head :y dec)
    "R" (update head :x inc)
    "L" (update head :x dec)))


(defn move [rope dir]
  "Apply the move in direction dir to the pos of head and tail, if the head and tail are no longer touching, update the
  tail position to the old head position. Track the history of the tail in :hist"
  (let [old-head (:head rope)
        new-head (move-head old-head dir)
        tail (:tail rope)
        hist (:hist rope)]
    (if (touching? new-head tail)
      {:head new-head :tail tail :hist hist}
      {:head new-head :tail old-head :hist (cons old-head hist)})))


(def init {:head {:x 0 :y 0}
           :tail {:x 0 :y 0}
           :hist [{:x 0 :y 0}]})


(defn part1 []
  (->> input-file
     (core/load-split)
     (map #(str/split % #" "))
     (map expand-direction)
     (flatten)
     (reduce move init)
     :hist
     (distinct)
     (count)))


(defn move-knot [knot ahead]
  (let [xdiff (- (:x ahead) (:x knot))
        ydiff (- (:y ahead) (:y knot))]
    (cond
      (touching? knot ahead) knot
      (and (< 1 xdiff) (= 0 ydiff)) (update knot :x inc) ; move up 1
      (and (> 1 xdiff) (= 0 ydiff)) (update knot :x dec) ; move down 1
      (and (= 0 xdiff) (< 1 ydiff)) (update knot :y inc) ; move right 1
      (and (= 0 xdiff) (> 1 ydiff)) (update knot :y dec) ; move left 1
      (and (< 0 xdiff) (< 0 ydiff)) (update (update knot :x inc) :y inc) ; move up 1 & right 1
      (and (< 0 xdiff) (> 0 ydiff)) (update (update knot :x inc) :y dec) ; move up 1 & left 1
      (and (> 0 xdiff) (< 0 ydiff)) (update (update knot :x dec) :y inc) ; move down 1 & right 1
      (and (> 0 xdiff) (> 0 ydiff)) (update (update knot :x dec) :y dec)))) ; move down 1 & left 1


(defn move-knots [new-knots to-move]
  (let [ahead (last new-knots)]
    (conj new-knots (move-knot to-move ahead))))


(defn move2 [rope dir]
  (let [old-head (first (:knots rope))
        new-head (move-head old-head dir)
        new-knots (reduce move-knots [new-head] (rest (:knots rope)))
        result {:knots new-knots :hist (conj (:hist rope) (last new-knots))}]
    result))


(def init2 {:knots (vec (repeat 10 {:x 0 :y 0}))
            :hist [{:x 0 :y 0}]})


(defn part2 []
  (->> input-file
       (core/load-split)
       (map #(str/split % #" "))
       ;(take 4)
       (map expand-direction)
       (flatten)
       (reduce move2 init2)
       :hist
       (distinct)
       (count)))


