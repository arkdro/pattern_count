(ns pattern_count.filter
  (:require bia_utils.util)
  )

(defn find-aux [line re acc idx]
  (if (empty? line) (reverse acc)
      (let [matcher (re-matcher re line)]
        (if (re-find matcher)
          (recur (subs line 1) re (cons idx acc) (inc idx))
          (recur (subs line 1) re acc (inc idx)))
        )))

(defn find-items [line sub]
  (let [re (re-pattern (apply str ["^" sub]))]
    (find-aux line re () 1)
  ))

(defn count-items [line sub]
  (let [items (find-items line sub)
        n (count items)]
    n))

(defn add-one-k-mer [idx line k acc]
  (let [k-mer (bia_utils.util/get-one-k-mer idx line k)
        n (count-items line k-mer)
        new-acc (update-in acc [k-mer] (fnil inc 0))]
    new-acc))

(defn count-k-mers-aux [idx k acc len line]
  (if (bia_utils.util/is-data-available idx len k)
    (recur (inc idx) k
           (add-one-k-mer idx line k acc)
           len line)
    acc))

;; O(n^2), where n = length(line)
(defn count-k-mers [line k]
  (count-k-mers-aux 0 k {} (count line) line)
  )

