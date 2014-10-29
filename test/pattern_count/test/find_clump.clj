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

(deftest is-next-pos-good-for-clump-test
  (is (= false
         (pattern_count.find_clump/is-next-pos-good-for-clump
          10 0 2 [])))
  (is (= true
         (pattern_count.find_clump/is-next-pos-good-for-clump
          10 1 2 [0 5])))
  (is (= false
         (pattern_count.find_clump/is-next-pos-good-for-clump
          10 3 7 [0 5 7 32])))
  )

(deftest get-clump-aux2-test
  (is (= {:found false}
         (pattern_count.find_clump/get-clump-aux2
          10 2 "asa" 1 0 [0 13 17])))
  (is (= {:found true, :cnt 1, :pos 0, :rest [0 5 7]}
         (pattern_count.find_clump/get-clump-aux2
          10 1 "asa" 1 0 [0 5 7])))
  (is (= {:found true, :cnt 2, :pos 0, :rest [0 5 27]}
         (pattern_count.find_clump/get-clump-aux2
          10 2 "asa" 1 0 [0 5 27])))
  (is (= {:found false}
         (pattern_count.find_clump/get-clump-aux2
          10 2 "asa" 1 5 [5 27])))
  )

