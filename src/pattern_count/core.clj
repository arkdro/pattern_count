(ns pattern_count.core
  {:doc "pattern count"}
  (:use clj-getopts.core)
  (:require pattern_count.filter)
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

(defn -main [& args]
  (let [opts (getopts (options "is" {:infile :arg
                                     :skip :arg}) args)
        data (read-data opts)]
    (println "data:" data)))

