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

(defn is-next-pos-good-for-clump [window need idx cur-pos positions]
  (let [
        next-pos (get positions idx)
        delta (- next-pos cur-pos)
        ]
    )
  )

;; results should be processed as a such:
;; found - continue, starting from the rest of positions (t positions consumed)
;; not found - continue, starting from the second position of the whole list
(defn get-clump-aux [window need k-mer cnt cur-pos positions]
  (cond
    (>= cnt need) {:found true, :cnt cnt, :pos cur-pos, :rest positions}
    (is-next-pos-good-for-clump window need cur-pos positions)
      (get-clump-aux window need k-mer (inc cnt) cur-pos (rest positions))
    :default {:found false}
    )
  )

(defn get-clump [window need k-mer positions]
  (if (> (count positions) 1)
    (get-clump-aux window need k-mer 1 (first positions) (rest positions))
    [k-mer nil]
    )
  )

(defn iter-k-mers [line k window t]
  (let [
        positions (collect-k-mers-positions 0 k window t {} (count line) line)
        clumps (map #(get-clump window t %) positions)
        ]
    clumps)
  )

