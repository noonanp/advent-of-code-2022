(ns advent-of-code-2022.day7
  (:require [advent-of-code-2022.core :as core]
            [clojure.string :as str]))


(def input-file "resources/2022-day7.txt")

;;every group of commands ends in an ls
;(filter
;  #(and (not= (last %) "$ ls") (str/starts-with? % "$"))
;  (partition-by #(str/starts-with? % "$") (core/load-split input-file)))


(defn raw-size [contents]
  (->>
    contents
    (filter #(not (str/starts-with? % "dir")))
    (map #(Integer. (first (str/split % #" "))))
    (reduce +)))


(defn combine-path [path cmd]
  "Combine the current path with a single command to produce a new path"
  (cond
    (= cmd "$ cd /") ["/"]
    (= cmd "$ ls") path
    (= cmd "$ cd ..") (vec (drop-last path))
    (str/starts-with? cmd "$ cd ") (conj path (nth (str/split cmd #" ") 2))))


(defn path [start-path cmds]
  "Take a starting path (represented as a vec of directories), and a seq of commands and produce a new path"
  (reduce combine-path start-path cmds))


(defn create-dir [dirs cmds-contents]
  "Take a map of directories, and vector containing a list of commands and the ls output and update the map with a new directory entry. The map must always contain a :cur entry to track the current directory"
  (let [cmds (first cmds-contents)
        contents (second cmds-contents)
        cur-dir (:cur dirs)
        this-dir (path cur-dir cmds)]
    (->
      dirs
      (assoc :cur this-dir)
      (assoc this-dir (raw-size contents)))))


(defn calc-raw-sizes [input]
  (->> input-file
       (core/load-split)
       (partition-by #(str/starts-with? % "$")) ;; partition list each time output changes from command to ls output
       (partition 2) ;; group the partitions into pairs
       (reduce create-dir {:cur ["/"]}))) ;; "reduce" the pairs into map of path to raw size. Path is a vec of directories


;; This will calculate the raw sizes of each dir (the size of just the files in a dir, not including sub dirs)
;; But it doesn't actually solve the problem lol
(defn part1 []
  (let [raw-sizes (dissoc (calc-raw-sizes input-file) :cur)]
    (group-by
      #(count (key %))
      raw-sizes)))


