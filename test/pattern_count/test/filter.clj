(ns pattern_count.test.filter
  (:use [pattern_count.filter])
  (:use [clojure.test]))

(deftest find-items-test
  (let [res (find-items "asadfasasa" "asa")]
    (is (= res [1 6 8]))))

(deftest count-items-test
  (let [res (count-items "asadfasasa" "asa")]
    (is (= res 3))))

(deftest add-one-k-mer-test
  (is (= {"qwe" 1} (add-one-k-mer 0 "qwer" 3 {})))
  (is (= {"qwe" 2 "asd" 3} (add-one-k-mer 0 "qwer" 3 {"qwe" 1 "asd" 3})))
  (is (= {"wer" 1 "asd" 3} (add-one-k-mer 1 "qwer" 3 {"asd" 3})))
  )

