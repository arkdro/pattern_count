(ns pattern_count.freq_array
  (:require bia_utils.util)
  )

(defn count-k-mers-aux [idx k acc len line]
  (if (bia_utils.util/is-data-available idx len k)
    (recur (inc idx) k
           (bia_utils.util/add-one-k-mer idx line k acc)
           len line)
    acc))

(defn count-k-mers [line k]
  (count-k-mers-aux 0 k {} (count line) line))

