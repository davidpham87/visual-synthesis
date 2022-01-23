(ns wyssacademy.visual-synthesis.components.list
  (:require [wyssacademy.visual-synthesis.utils :as wvu])
  (:refer-clojure :exclude [list]))

(def styles
  {:list {:comp :ul, :class ["bg-white rounded-lg shadow divide-y divide-gray-100"]},
   :list-item {:comp :li :class ["p-3"]}})

(declare list)
(declare list-item)

(wyssacademy.visual-synthesis.utils/styles->components-litteral
 {:list {:comp :ul, :class ["bg-white rounded-lg shadow divide-y divide-gray-100"]},
  :list-item {:comp :li :class ["p-3"]}})

(comment
  (list-item {:class ["px-3"]} [:div :hello :world])
  (list {:class ["px-3"]}
        (list-item {:class ["px-10"]} [:div :hello :world])
        (list-item {:class ["px-10"]} [:div :hello :world]))
  )
