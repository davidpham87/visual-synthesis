(ns wyssacademy.visual-synthesis.details
  (:require
   ["@material-tailwind/react/Button$default" :as button]
   ["@material-tailwind/react/Heading6$default" :as heading-6]
   [re-frame.core :as rf :refer (subscribe)]
   [reagent.core :as reagent]
   [wyssacademy.visual-synthesis.components.dropdown :as wvcd]
   [wyssacademy.visual-synthesis.components.tabs :as tabs-ns :refer (tab-content)]
   [wyssacademy.visual-synthesis.db]
   [wyssacademy.visual-synthesis.events :as events]
   [wyssacademy.visual-synthesis.subs :as subs]))

(defn display-interactions [interactions]
  (into [:ul.list-disc.pl-4] (map (fn [m] [:li.mb-4 {} [:div.text-justify (:link-description m)]]))
        interactions))

(def previous-interaction (reagent/atom {}))
(def previous-interaction-impacted (reagent/atom {}))
(def previous-interaction-influence (reagent/atom {}))

(defn detail-content []
  (let [interactions-by-origin (subscribe [::subs/interactions-by-origin [:link-description]])
        interactions-by-destination (subscribe [::subs/interactions-by-destination [:link-description]])
        interaction (subscribe [::subs/ui-states-value :selected-landscape])
        ->dropdown-item
        (fn [m] [wvcd/item
                 {:color :teal :ripple :light
                  :on-click #(rf/dispatch [::events/set-hover-landscape (:key m)])}
                 (:label m)])]
    (fn []
      (when @interaction
        (reset! previous-interaction-influence (get @interactions-by-origin @interaction))
        (reset! previous-interaction-impacted (get @interactions-by-destination @interaction))
        (reset! previous-interaction @interaction))
      [:div.flex.flex-col.h-full
       [:> heading-6 {:color :teal}
        (wyssacademy.visual-synthesis.db/categories-map @previous-interaction)]
       [:div.flex.justify-between.w-full.items-center.mb-4
        ^{:key @previous-interaction}
        [:div.flex.items-center.gap-4
         (into [wvcd/dropdown {:color :teal
                              :button-text "Element"
                              :size :sm}]
               (map ->dropdown-item) (sort-by :label wyssacademy.visual-synthesis.db/categories))]
        [:> button {:color :red} "Source"]]
       [:div.flex.w-full.gap-4
        [:div {:class ["w-1/2" :flex :gap-2 :flex-col]}
         [:div "Influence"]
         [:div
          [display-interactions @previous-interaction-influence]]]
        [:div {:class ["w-1/2" :flex :gap-2 :flex-col]}
         "Impacted"
         [:div
          [display-interactions @previous-interaction-impacted]]]]])))

(defn details []
  [tab-content {:class ["h-full"]
                :style {:min-height "35rem"}}
   [detail-content]])
