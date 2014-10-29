(ns pattern_count.test.find_clump
  ;; (:use clojure.tools.trace)
  (:use [pattern_count.find_clump])
  (:use [clojure.test]))

;; (trace-ns 'bia_utils.util)
;; (trace-ns 'pattern_count.find_clump)

(deftest store-k-mer-position-test
  (is (= {"asa" [0]}
         (pattern_count.find_clump/store-k-mer-position
          0 "asadfasasa" 3 4 1 {})))
  (is (= {"asa" [0 5]}
         (pattern_count.find_clump/store-k-mer-position
          5 "asadfasasa" 3 4 1 {"asa" [0]})))
  (is (= {"dfa" [3] "asa" [0]}
         (pattern_count.find_clump/store-k-mer-position
          3 "asadfasasa" 3 4 1 {"asa" [0]})))
  )

(deftest collect-k-mers-positions-test
  (is (= {"sas" [6], "fas" [4], "dfa" [3], "adf" [2], "sad" [1], "asa" [0 5 7]}
         (pattern_count.find_clump/collect-k-mers-positions
          0 3 5 2 {} 10 "asadfasasa")))
  )

(deftest is-distance-short-test
  (is (= true (pattern_count.find_clump/is-distance-short 3 10 0 2)))
  (is (= false (pattern_count.find_clump/is-distance-short 3 10 0 12)))
  (is (= false (pattern_count.find_clump/is-distance-short 3 10 0 10)))
  (is (= true (pattern_count.find_clump/is-distance-short 3 10 0 6)))
  (is (= true (pattern_count.find_clump/is-distance-short 3 10 0 7)))
  (is (= false (pattern_count.find_clump/is-distance-short 3 10 0 8)))
  (is (= true (pattern_count.find_clump/is-distance-short 2 4 0 2)))
  (is (= false (pattern_count.find_clump/is-distance-short 2 4 0 3)))
  )

(deftest is-next-pos-good-for-clump-test
  (is (= false
         (pattern_count.find_clump/is-next-pos-good-for-clump
          3 10 0 2 [])))
  (is (= false
         (pattern_count.find_clump/is-next-pos-good-for-clump
          3 10 1 5 [5])))
  (is (= true
         (pattern_count.find_clump/is-next-pos-good-for-clump
          3 10 1 2 [0 5])))
  (is (= false
         (pattern_count.find_clump/is-next-pos-good-for-clump
          3 10 3 7 [0 5 7 32])))
  )

(deftest get-clump-aux2-test
  (is (= {:found false}
         (pattern_count.find_clump/get-clump-aux2
          3 10 2 "asa" 1 0 [0 13 17])))
  (is (= {:found true, :cnt 1, :pos 0, :rest [0 5 7]}
         (pattern_count.find_clump/get-clump-aux2
          3 10 1 "asa" 1 0 [0 5 7])))
  (is (= {:found true, :cnt 2, :pos 0, :rest [0 5 27]}
         (pattern_count.find_clump/get-clump-aux2
          3 10 2 "asa" 1 0 [0 5 27])))
  (is (= {:found false}
         (pattern_count.find_clump/get-clump-aux2
          3 10 2 "asa" 1 5 [5 27])))
  )

(deftest get-clump-aux1-test
  (is (= ["asa" nil]
         (pattern_count.find_clump/get-clump-aux1
          3 10 2 "asa" [0 13 27])))
  )

(deftest get-clump-test
  (is (= ["asa" nil]
         (pattern_count.find_clump/get-clump
          3 10 2 ["asa" []])))
  (is (= ["asa" nil]
         (pattern_count.find_clump/get-clump
          3 10 2 ["asa" [0]])))
  (is (= ["asa" nil]
         (pattern_count.find_clump/get-clump
          3 10 2 ["asa" [0 13 27]])))
  (is (= ["asa" 0]
         (pattern_count.find_clump/get-clump
          3 10 2 ["asa" [0 5 27]])))
  (is (= ["asa" 15]
         (pattern_count.find_clump/get-clump
          3 10 2 ["asa" [0 15 22]])))
  )

(deftest iter-k-mers-test
  (is (= '()
         (pattern_count.find_clump/iter-k-mers "asadfasasa" 3 10 5)))
  (is (= '()
         (pattern_count.find_clump/iter-k-mers "asadsfasasa" 3 10 3)))
  (is (= '(["asa" 0])
         (pattern_count.find_clump/iter-k-mers "asadfasasa" 3 10 3)))
  (is (= '(["sas" 6] ["fas" 4] ["dfa" 3] ["adf" 2] ["sad" 1] ["asa" 0])
         (pattern_count.find_clump/iter-k-mers "asadfasasa" 3 10 1)))
  )

