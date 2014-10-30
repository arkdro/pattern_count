(ns pattern_count.core
  {:doc "pattern count"}
  (:use clj-getopts.core)
  (:require pattern_count.filter)
  (:require pattern_count.find_clump)
  (:require pattern_count.freq_array)
  (:use clojure.tools.trace)
  (:gen-class)
  )

;;(trace-ns 'pattern_count.filter)

(defn read-lines [fd skip]
  (let [
        lines (line-seq fd)
        lines2 (drop skip lines)
        [line1 line2] (take 2 lines2)
        res [line1 line2]
        ]
    res))

(defn read-file [fname skip]
  (let [acc (with-open [fd (clojure.java.io/reader fname)]
              (doall
               (read-lines fd skip)
               ))]
    acc))

(defn read-data [opts]
  (let [fname (get opts :infile)
        skip-str (get opts :skip "0")
        skip (Integer/parseInt skip-str)
        ]
    (read-file fname skip)))

(defn iter-k-mers [args]
  (let [opts (getopts (options "is" {:infile :arg
                                     :k :arg
                                     :l :arg
                                     :t :arg
                                     :skip :arg}) args)
        [line1 _] (read-data opts)
        k (Integer/parseInt (get opts :k))
        l (Integer/parseInt (get opts :l))
        t (Integer/parseInt (get opts :t))
        res (pattern_count.find_clump/iter-k-mers line1 k l t)
        sorted (sort (map first res))
        amount (count res)
        ]
    (println "amount:" amount)
    ;; (println "res:" res)
    (println "sorted" sorted)
    ))

(defn count-items-by-filter [args]
  (let [opts (getopts (options "is" {:infile :arg
                                     :skip :arg}) args)
        [line1 line2] (read-data opts)
        res (pattern_count.filter/count-items line1 line2)]
    res))

(defn count-items-by-array [args]
  (let [opts (getopts (options "is" {:infile :arg
                                     :skip :arg}) args)
        [line1 line2] (read-data opts)
        res (pattern_count.freq_array/count-items line1 line2)]
    res))

(defn count-k-mers-by-filter [args]
  (let [opts (getopts (options "is" {:infile :arg
                                     :k :arg
                                     :skip :arg}) args)
        k (Integer/parseInt (get opts :k))
        [line1] (read-data opts)
        res (pattern_count.filter/count-k-mers line1 k)]
    res))

(defn count-k-mers-by-array [args]
  (let [opts (getopts (options "is" {:infile :arg
                                     :k :arg
                                     :skip :arg}) args)
        k (Integer/parseInt (get opts :k))
        [line1] (read-data opts)
        res (pattern_count.freq_array/count-k-mers line1 k)]
    res))

(defn -main [& args]
  (let [opts (getopts (options "is" {:fun :arg}) args)]
    (case (get opts :fun)
      "count-k-mers-by-array" (count-k-mers-by-array args)
      "count-k-mers-by-filter" (count-k-mers-by-filter args)
      "count-items-by-array" (count-items-by-array args)
      "count-items-by-filter" (count-items-by-filter args)
      "iter-k-mers" (iter-k-mers args)
      )
    ))

