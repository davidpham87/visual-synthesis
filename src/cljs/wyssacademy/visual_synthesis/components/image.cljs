(ns wyssacademy.visual-synthesis.components.image
  (:require [wyssacademy.visual-synthesis.utils :refer (defc)]))

(defc image [{:keys [raised? rounded? class] :as m} & _]
  [:img (merge
         {:class (into ["max-w-full" "h-auto" "align-middle" "border-none"
                        (if rounded? "rounded-full" "rounded-xl")
                        (when raised?  "shadow-lg")] class)}
         (dissoc m :class))])
