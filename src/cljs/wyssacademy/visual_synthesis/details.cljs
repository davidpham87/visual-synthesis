(ns wyssacademy.visual-synthesis.details
  (:require
   ["@material-tailwind/react/Button$default" :as button]
   ["@material-tailwind/react/Card$default" :as card]
   ["@material-tailwind/react/CardBody$default" :as card-body]
   ["@material-tailwind/react/CardHeader$default" :as card-header]
   [re-frame.core :as rf :refer (subscribe)]
   [wyssacademy.visual-synthesis.components.typography :refer (icon)]
   [wyssacademy.visual-synthesis.subs :as subs]))

(defn detail-content []
  (let [interactions-by-origin (subscribe [::subs/interactions-by-origin [:link-description]])
        interactions-by-destination (subscribe [::subs/interactions-by-destination [:link-description]])
        interaction (subscribe [::subs/ui-states-value :selected-landscape])]
    (fn []
      (tap> {:maps @interactions-by-origin
             :k @interaction
             :m (str (get @interactions-by-origin @interaction))})
      [:> card-body {:class ["h-full"]}
       [:div "Details with studies for the interactions"]
       [:div {:class [:flex :gap-10]}
        [:div "Influence"]
        [:div (str (get @interactions-by-origin @interaction))]]
       [:div {:class [:flex :gap-10]}
        "Impacted"
        [:div (str (get @interactions-by-destination @interaction))]]
       [:> button {:color :red} "Source"]])))

(defn details []
  [:div.w-full
   [:> card {:class ["h-full"]}
    [:> card-header {:size :sm :color :cyan :icon-only true}
     [icon {:size :9xl :name "view_list"}]]
    [detail-content]]])
