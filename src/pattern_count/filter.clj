(ns pattern_count.filter)

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

