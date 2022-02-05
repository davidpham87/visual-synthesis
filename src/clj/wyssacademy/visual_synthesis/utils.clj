(ns wyssacademy.visual-synthesis.utils)

(defn ->component [f]
  (fn [m & rest]
    (if (map? m)
      (apply f m rest)
      (apply f {} (cons m rest)))))

(defmacro defc [symbol args & body]
  `(let [f# (fn ~args ~@body)]
     (def ~symbol (->component f#))))

(defmacro styles->components-litteral
  "Transform a configuration of a map of `{:component-name
  {:comp :html-tag :class [\"class-1\" \"class-2]}}` pairs into reagent
  component. Must generate code, for postcss to generate the css "
  [styles]
  `(do ~@(for [[k# comp+class#] styles
                :let [comp# (:comp comp+class#)
                      class# (:class comp+class#)]]
            `(defc ~(symbol (name k#)) [m# & rest#]
               (into [~comp#
                      (merge-with (comp vec concat) {:class ~class#} m#)]
                     rest#)))))

(defn styles->components
  "Insure the evaluation of the input before passing to styles->components-litteral"
  [styles]
  `(eval (let [m# ~styles] `(styles->components-litteral ~m#))))

(defmacro styles->components-print
  "Insure the evaluation of the input before passing to styles->components-litteral"
  [styles]
  `(let [m# ~styles] `(styles->components-litteral ~m#)))

(comment

  (defc hello-3 [{:keys [x] :as m} & children]
    (into [:div {:x (or x :unfound)} [:pre m]] children))

  (hello-3 {:y 3 :x 5} [:div 6] [:div 5] [:div 7])
  (hello-3 [:div 6] [:div 5] [:div 7])

  (styles->components-litteral
   {:thead {:comp :thead
            :class ["bg-gray-50"]}
    :th {:comp :th
         :class ["px-6" "py-2" "text-sm" "text-gray-800"]}
    :td {:comp :td
         :class ["px-6" "py-4" "text-md" "text-gray-800"]}
    :tr {:comp :tr
         :class ["whitespace-nowrap"]}})

  (tr {:class ["px-5"]} [:div 4])


  (let [m {:thead {:comp :thead
                   :class ["bg-gray-50"]}
           :th {:comp :th
                :class ["px-6" "py-2" "text-sm" "text-gray-800"]}
           :td {:comp :td
                :class ["px-6" "py-4" "text-md" "text-gray-800"]}
           :tr {:comp :tr
                :class ["whitespace-nowrap"]}}]
    (styles->components m)))
