(ns pattern_count.find_clump
  (:require bia_utils.util)
  )

(defn add-one-position [coll pos]
  (if (empty? coll)
    [pos]
    (conj coll pos)))

(defn store-k-mer-position [idx line k window t acc]
  (let [k-mer (bia_utils.util/get-one-k-mer idx line k)
        new-acc (update-in acc [k-mer] #(add-one-position % idx))]
    new-acc))

(defn collect-k-mers-positions [idx k window t acc len line]
  (if (bia_utils.util/is-data-available idx len k)
    (recur (inc idx) k window t
           (store-k-mer-position idx line k window t acc)
           len line)
    acc))

(defn iter-k-mers [line k window t]
  (collect-k-mers-positions 0 k window t {} (count line) line)
  )

