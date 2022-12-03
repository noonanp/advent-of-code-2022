(ns advent-of-code-2022.core)


(defn load-split [f]
   (let [result (clojure.string/split-lines
                  (slurp f))]
     result))


(defn test-load []
  (load-split "resources/2021-day1.txt"))