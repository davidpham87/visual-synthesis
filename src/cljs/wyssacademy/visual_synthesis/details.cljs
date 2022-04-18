(ns wyssacademy.visual-synthesis.details
  (:require
   ["@material-tailwind/react/Button$default" :as button]
   ["@material-tailwind/react/Heading6$default" :as heading-6]
   ["@material-tailwind/react/Icon$default" :as icon]
   [re-frame.core :as rf :refer (subscribe)]
   [reagent.core :as reagent]
   [wyssacademy.visual-synthesis.components.dropdown :as wvcd]
   [wyssacademy.visual-synthesis.components.list :as wvcl]
   [wyssacademy.visual-synthesis.components.tabs :as tabs-ns :refer (tab-content)]
   [wyssacademy.visual-synthesis.db]
   [wyssacademy.visual-synthesis.events :as events]
   [wyssacademy.visual-synthesis.subs :as subs]))

(defn display-interactions [interactions]
  [:div.h-96 {:style {:overflow-y :auto}}
   (into [:ul.list-disc.pl-4.pr-4]
         (map (fn [m] [:li.mb-4 {} [:div.text-justify (:link-description m)]]))
         interactions)])

(def previous-interaction (reagent/atom {}))
(def previous-interaction-impacted (reagent/atom {}))
(def previous-interaction-influence (reagent/atom {}))

(defn details-links []
  (let [interactions         (subscribe [::subs/interactions-by-link [:studies]])
        selected-source      (rf/subscribe [::subs/ui-states-value :selected-source])
        selected-destination (rf/subscribe [::subs/ui-states-value :selected-destination])
        show-source?         (reagent/atom #{})
        studies (subscribe [::subs/studies])]
    (fn []
      [:div.overflow-y-scroll {:style {:height 500}}
        (into
         ^{:key [@selected-source @selected-destination]}
         [wvcl/list {:class [:-pl-10]}]
         (for [m    (into [] (comp (map :studies) cat)
                          (get @interactions [@selected-source @selected-destination]))
               :let []]
           ^{:key (str (:link-description m) (:out m) (:in m))}
           [wvcl/list-item {:class ["hover:bg-gray-50" "hover:text-black"]}
            [:div.flex.flex-col.text-justify
             [:p {:on-click #(swap! show-source? (if (@show-source? (:study m)) disj conj)
                                    (:study m))}
              (:explanation m) " "]
             [:div.w-full
              [:> button {:size :sm
                          :on-click #(swap! show-source? (if (@show-source? (:study m)) disj conj)
                                            (:study m))
                          :class    [:ml-auto] :color :red}
               [:> icon {:name "article" :size :sm}]
               "Source"]]
             ;; todo make animation
             [:div.transition..ease-in.duration-200
              {:style {:height (if (contains? @show-source? (:study m)) "100%" 0)}}
              (when (contains? @show-source? (:study m))
                (let [{:keys [url title authors year journal doi abstract]} (get @studies (:study m))]
                  [:div.text-gray-700
                   [:p.mt-2.mb-2 authors]
                   [:p.mb-2 "\"" title "\","]
                   [:p.mb-2 [:em journal] " (" year ")"]
                   [:div.mb-2 doi]
                   [:a.text-blue-400.mb-2 {:href url :target "_blank"} url]
                   [:p.text-justify.mb-4.mt-4.pl-2.pr-2.text-gray-500 abstract]]))]]]))])))

(defn detail-content []
  (let [interactions-by-origin (subscribe [::subs/interactions-by-origin [:link-description]])
        interactions-by-destination (subscribe [::subs/interactions-by-destination [:link-description]])
        interaction (subscribe [::subs/ui-states-value :selected-landscape])
        ->dropdown-item
        (fn [m] [wvcd/item
                 {:color :teal :ripple :light
                  :on-click #(rf/dispatch [::events/set-hover-landscape (:key m)])}
                 (:label m)])
        tab (reagent/atom nil)]
    (fn []
      (when @interaction
        (reset! previous-interaction-influence (get @interactions-by-origin @interaction))
        (reset! previous-interaction-impacted (get @interactions-by-destination @interaction))
        (reset! previous-interaction @interaction))
      [:div.flex.flex-col.h-full
       [:> heading-6 {:color :teal}
        (or (wyssacademy.visual-synthesis.db/categories-map @previous-interaction)
            "Select an element to start")]
       [:div.flex.justify-between.w-full.items-center.mb-4
        ^{:key @previous-interaction}
        [:div.flex.items-center.gap-4
         (into [wvcd/dropdown {:color :teal
                              :button-text "Element"
                              :size :sm}]
               (map ->dropdown-item) (sort-by :label wyssacademy.visual-synthesis.db/categories))]
        [:> button {:color :red} "Source"]]

       [:div.flex.gap-2.mb-4
        [:> button {:color :green :on-click #(reset! tab :influence)} "Influence"]
        [:> button {:color :green :on-click #(reset! tab :impacted)} "Impacted"]]
       [:div.flex.w-full
        (case @tab
          :impacted [display-interactions @previous-interaction-impacted]
          [display-interactions @previous-interaction-influence])]])))

(defn details-summary []
  [tab-content {:class ["h-full"]
                :style {:min-height "35rem"}}
   [detail-content]])

(comment
  (let [m                    @(subscribe [::subs/interactions-by-link [:studies]])
        selected-source      @(rf/subscribe [::subs/ui-states-value :selected-source])
        selected-destination @(rf/subscribe [::subs/ui-states-value :selected-destination])]
    [selected-source         selected-destination]
    #_(get m [selected-source selected-destination]))


  )
