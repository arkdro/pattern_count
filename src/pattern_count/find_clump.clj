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

(defn is-next-pos-good-for-clump [window idx cur-pos positions]
  (cond
    (empty? positions) false
    (<= (count positions) idx) false
    :default (let [next-pos (nth positions idx)
                   delta (- next-pos cur-pos)]
               (<= delta window))))

;; results should be processed as a such:
;; found - continue, starting from the rest of positions (t positions consumed)
;; that are farther by window size (for this window position the clump
;; has been found) from cur-pos. In fact, continue is unnecessary.
;; not found - continue, starting from the second position of the whole list
(defn get-clump-aux2 [window need k-mer cnt cur-pos positions]
  (cond
    (>= cnt need) {:found true, :cnt cnt, :pos cur-pos, :rest positions}
    (is-next-pos-good-for-clump window cnt cur-pos positions)
      (recur window need k-mer (inc cnt) cur-pos positions)
    :default {:found false}))

(defn get-clump-aux1 [window need k-mer positions]
  (if (empty? positions)
    [k-mer nil]
    (let [res (get-clump-aux2 window need k-mer 1
                              (first positions)
                              (rest positions))]
      (if (:found res)
        [k-mer (:pos res)]
        (recur window need k-mer (rest positions))))))

(defn get-clump [window need [k-mer positions]]
  (if (>= (count positions) need)
    (get-clump-aux1 window need k-mer positions)
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

