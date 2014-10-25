(ns pattern_count.test.core
  (:import [java.io BufferedReader StringReader])
  (:use [pattern_count.core])
  (:use [clojure.test]))

(deftest read-lines-test
  (let [
        [line1 line2] (read-lines
                       (BufferedReader. (StringReader.
                                         "QWER\nASDF\nZXCV\n\n3421"))
                       0)
        [line3 line4] (read-lines
                       (BufferedReader. (StringReader.
                                         "QWER\nASDF\nZXCV\n\n3421"))
                       1)
        ]
    (is (= line1 "QWER"))
    (is (= line2 "ASDF"))
    (is (= line3 "ASDF"))
    (is (= line4 "ZXCV"))
    )
  )

