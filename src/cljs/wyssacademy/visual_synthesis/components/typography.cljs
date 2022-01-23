(ns wyssacademy.visual-synthesis.components.typography
  (:refer-clojure :exclude [quote])
  (:require
   [wyssacademy.visual-synthesis.utils :refer (styles->components-litteral defc)]))

(def styles
  {:h1 {:comp :h1
        :class ["text-6xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h2 {:comp :h2
        :class ["text-5xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h3 {:comp :h3
        :class ["text-4xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h4 {:comp :h4
        :class ["text-3xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h5 {:comp :h5
        :class ["text-2xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h6 {:comp :h6
        :class ["text-xl" "font-serif" "font-bold" "leading-normal" "mt-0" "mb-2"]}
   :leading-text {:comp :p
                  :class ["text-lg" "font-light" "leading-relaxed" "mt-6" "mb-4"]}
   :paragraph {:comp :p
               :class ["text-base" "font-light" "leading-relaxed" "mt-0" "mb-4"]}
   :small {:comp :small
           :class ["font-normal" "leading-normal" "mt-0" "mb-4"]}})

(declare h1)
(declare h2)
(declare h3)
(declare h4)
(declare h5)
(declare h6)
(declare leading-text)
(declare paragraph)
(declare small)

(styles->components-litteral
 {:h1 {:comp :h1
        :class ["text-6xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h2 {:comp :h2
        :class ["text-5xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h3 {:comp :h3
        :class ["text-4xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h4 {:comp :h4
        :class ["text-3xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h5 {:comp :h5
        :class ["text-2xl" "font-serif" "font-bold" "leading-normal" "mt-0"
                "mb-2"]}
   :h6 {:comp :h6
        :class ["text-xl" "font-serif" "font-bold" "leading-normal" "mt-0" "mb-2"]}
   :leading-text {:comp :p
                  :class ["text-lg" "font-light" "leading-relaxed" "mt-6" "mb-4"]}
   :paragraph {:comp :p
               :class ["text-base" "font-light" "leading-relaxed" "mt-0" "mb-4"]}
   :small {:comp :small
           :class ["font-normal" "leading-normal" "mt-0" "mb-4"]}})

(defc quote [{:keys [footer cite] :as m} & children]
  [:div.mb-2 (dissoc m :footer :cite)
   (into [:p {:class ["text-base" "font-light" "leading-relaxed" "mt-0" "mb-2"]}]
         children)
   [:footer footer
    [:cite cite]]])
