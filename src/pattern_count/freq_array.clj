(ns pattern_count.freq_array
  (:require bia_utils.util)
  )

(defn count-k-mers-aux [idx k acc len line]
  (if (bia_utils.util/is-data-available idx len k)
    (recur (inc idx) k
           (bia_utils.util/add-one-k-mer idx line k acc)
           len line)
    acc))

;; O(n), where n = length(line)
(defn count-k-mers [line k]
  (count-k-mers-aux 0 k {} (count line) line))

(defn count-items [text k-mer]
  (let [all-counts (count-k-mers text (count k-mer))
        res (get all-counts k-mer)]
    res))

