(ns advent-of-code-2022.day8
  (:require [advent-of-code-2022.core :as core]))

(def input-file "resources/2022-day8.txt")


(defn col-str [rows pos]
  "Create a string from the chars at pos in each row"
  (->>
    rows
    (map #(nth % pos))
    (reduce str)))


(defn row-to-col [rows]
  "Pivots the rows 90 degrees, converting a vec of row strings to a vec of col strings, hardcoded to 100 chars in each row"
  (map
    #(col-str rows %)
    (range 99))) ; 0-99 = 100 chars per row


(defn str-vec [str]
  "converts a string of digits to a vec of int"
  (mapv
    #(Character/digit % 10)
    (char-array str)))


(defn is-visible-row? [vec pos]
  "checks if the digit at pos is the greatest value from either the start or end of the seq"
  (let [start (subvec vec 0 pos)
        end (subvec vec (inc pos))
        val (get vec pos)]
    (if (or (= pos 0) (= pos (dec (count vec)))) ;if pos is start or end return true
      true
      (or (< (apply max start) val) ; is val the max compared to the values to the start of the vec
          (< (apply max end) val))))) ; is val the max compared to the values to the end of the vec


(defn is-visible? [rows cols row col]
  (or (is-visible-row? (get rows row) col)
      (is-visible-row? (get cols col) row)))


(defn part1 []
  (let [input (core/load-split input-file)
        rows (mapv str-vec input)
        cols (mapv str-vec (row-to-col input))]
    (count
      (filter
        identity
        (for [row (range 99)
              col (range 99)]
          (is-visible? rows cols row col))))))


(defn visible-count-row [seq pos]
  "counts the number of digits in the seq that are less than the value at pos in either direction"
  (let [start (vec (reverse (subvec seq 0 pos))) ; the digits from pos moving towards the start
        end (subvec seq (inc pos)) ; the digits from pos moving towards the end
        val (get seq pos)
        vis-start (count (take-while #(< % val) start)) ;count the digits val is greater than moving towards the start
        vis-end (count (take-while #(< % val) end))] ;count the digits val is greater than moving towards the end
    ; ugly code for correcting the result. take-while will stop at the first greater number, not counting it. However we can't
    ; just add 1 as if a tree can see all the way to the edge it will be correct and doesn't need it's result changed.
    ; Also if a tree is on the edge it is also correctly 0. :(
    (* (if (and (not= vis-start (count start)) ;not on edge
                (not-empty start)) ;not the full set
         (inc vis-start)
         vis-start)
       (if (and (not= vis-end (count end))  ;not on edge
                (not-empty end)) ;not the full set
         (inc vis-end)
         vis-end))))


(defn visible-count [rows cols row col]
  (* (visible-count-row (get rows row) col)
     (visible-count-row (get cols col) row)))


(defn part2 []
  (let [input (core/load-split input-file)
        rows (mapv str-vec input)
        cols (mapv str-vec (row-to-col input))]
    (apply max
      (for [row (range 99)
            col (range 99)]
        (visible-count rows cols row col)))))


