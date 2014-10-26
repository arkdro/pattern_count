(defproject pattern_count "1.0.0-SNAPSHOT"
  :description "pattern count. Bioinformatics algorithms"
  :main pattern_count.core
  :extra-classpath-dirs [
                         ".lein-git-deps/bia_utils/src"
                         ]
  :dependencies [
                  [org.clojure/clojure "1.4.0"]
                  [clj-getopts "0.0.2"]
                  [org.clojure/tools.trace "0.7.5"]
                ])

