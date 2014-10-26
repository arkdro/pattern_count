(ns pattern_count.test.freq_array
  ;; (:use clojure.tools.trace)
  (:use [pattern_count.freq_array])
  (:use [clojure.test]))

;; (trace-ns 'bia_utils.util)
;; (trace-ns 'pattern_count.freq_array)

(deftest count-k-mers-test
  (is (= {"sas" 1, "fas" 1, "dfa" 1, "adf" 1, "sad" 1, "asa" 3}
         (pattern_count.freq_array/count-k-mers "asadfasasa" 3)))
  )

