(ns pattern_count.core
  {:doc "pattern count"}
  (:use clj-getopts.core)
  (:require pattern_count.filter)
  (:use clojure.tools.trace)
  (:gen-class)
  )

;;(trace-ns 'pattern_count.filter)

(defn read-data [opts]
  (let [fname (get opts :infile)
        acc (with-open [fd (clojure.java.io/reader fname)]
              (let [[line1 line2] (take 2 (line-seq fd))]
                (pattern_count.filter/find-items line1 line2)))]
    acc))

(defn -main [& args]
  (let [opts (getopts (options "i?" {:infile :arg}) args)
        data (read-data opts)]
    (println "data:" data)))

