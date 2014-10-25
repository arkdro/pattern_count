(ns pattern_count.test.filter
  (:use [pattern_count.filter])
  (:use [clojure.test]))

(deftest find-items-test
  (let [res (find-items "asadfasasa" "asa")]
    (is (= res [1 6 8]))))

