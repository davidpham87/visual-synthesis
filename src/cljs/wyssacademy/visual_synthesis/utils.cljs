(ns wyssacademy.visual-synthesis.utils
  (:require-macros [wyssacademy.visual-synthesis.utils]))

(defn ->component [f]
  (fn [m & rest]
    (if (map? m)
      (apply f m rest)
      (apply f {} (cons m rest)))))
