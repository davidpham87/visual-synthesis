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

(sort {:lg "text-lg",
       :sm "text-sm",
       :8xl "text-8xl",
       :6xl "text-6xl",
       :xs "text-xs",
       :3xl "text-3xl",
       :4xl "text-4xl",
       :2xl "text-2xl",
       :9xl "text-9xl",
       :7xl "text-7xl",
       :xl "text-xl",
       :base "text-base",
       :5xl "text-5xl"})

(def size->class {:xs "text-xs"
                  :sm "text-sm"
                  :base "text-base"
                  :lg "text-lg"
                  :xl "text-xl"
                  :2xl "text-2xl"
                  :3xl "text-3xl"
                  :4xl "text-4xl"
                  :5xl "text-5xl"
                  :6xl "text-6xl"
                  :7xl "text-7xl"
                  :8xl "text-8xl"
                  :9xl "text-9xl"})

(defn icon [{:keys [name size] :as m}]
  [:span (update m :class (fnil into [])
                 ["material-icons"  "leadine-none"
                  (size->class size "text-base")])
   name])
