(ns wyssacademy.visual-synthesis.views
  (:require
   ["@material-tailwind/react/Button$default" :as button]
   ["@material-tailwind/react/Heading6$default" :as heading-6]
   ["@material-tailwind/react/Card$default" :as card]
   ["@material-tailwind/react/CardHeader$default" :as card-header]
   ["@material-tailwind/react/CardBody$default" :as card-body]
   [clojure.string :as str]
   [re-frame.core :as rf :refer (subscribe)]
   [wyssacademy.visual-synthesis.components.dropdown :as wvcd]
   [wyssacademy.visual-synthesis.components.list :as wvcl]
   [wyssacademy.visual-synthesis.components.navbar :refer (navbar)]
   [wyssacademy.visual-synthesis.components.tabs :as tabs-ns :refer (tabs tab-content)]
   [wyssacademy.visual-synthesis.components.typography :as typography]
   [wyssacademy.visual-synthesis.db :refer (categories categories-map)]
   [wyssacademy.visual-synthesis.details :refer (details-summary details-links)]
   [wyssacademy.visual-synthesis.events :as events]
   [wyssacademy.visual-synthesis.landscape :as wvl]
   [wyssacademy.visual-synthesis.subs :as subs]))

(defn header []
  [:div.mb-12
   [:div.fixed.z-40 {:class ["2xl:visible" "invisible"]}
    [:img.z-40 {:src "img/logo.webp"}]]
   [navbar]])

(defn footer []
  [:div.w-screen.overflow-x-hidden.px-20.pt-10.pb-10.bg-teal-700
   [:div.text-white {:class ["w-1/2"]}
    "Wyss Academy for Nature"
    [:br]
    "at the University of Bern"
    [:br]
    "Kochergasse 4"
    [:br]
    "3011 Bern — Switzerland"
    [:br]
    "+41 31 544 80 00"]])

(defn interactions-list []
  (let [on-click
        (fn [m k]
          (rf/dispatch [::events/set-ui-states :selected-source nil])
          (rf/dispatch [::events/set-ui-states :selected-destination nil])
          (rf/dispatch [::events/set-ui-states k (:key m)]))

        xf
        (fn [k]
          (map (fn [m] [wvcd/item {:color :teal :on-click (fn [] (on-click m k))}
                        (:label m)])))

        categories-sorted    (sort-by :label categories)
        selected-source      (rf/subscribe [::subs/ui-states-value :selected-source])
        selected-destination (rf/subscribe [::subs/ui-states-value :selected-destination])
        interaction          (subscribe [::subs/ui-states-value :selected-landscape])
        interactions         (subscribe [::subs/interactions-list-filtered])]

    (fn []
      [tab-content {}
       [:div
        [:> heading-6 {:color :teal}
         (or (wyssacademy.visual-synthesis.db/categories-map @interaction)
             "Select an element to start")]
        [:div.flex.gap-4.mb-4
         (into ^{:key @selected-source}
               [wvcd/dropdown {:size :sm :color :teal :button-text "Source"}]
               (xf :selected-source)
               categories-sorted)
         (into ^{:key @selected-destination} [wvcd/dropdown {:size :sm :color :teal :button-text "Destination"}]
               (xf :selected-destination)
               categories-sorted)
         [:> button {:color :blue :on-click #(on-click {:key nil} :selected-source)} "Show all"]]]

       [:div.overflow-y-scroll {:style {:height 500}}
        (into
         ^{:key [@selected-source @selected-destination]}
         [wvcl/list {:class [:-pl-10]}]
         (for [m @interactions]
           ^{:key (str (:link-description m) (:out m) (:in m))}
           [wvcl/list-item
            {:class    ["hover:bg-gray-50" "hover:text-black"]
             :on-click #(do
                          (rf/dispatch [::events/set-hover-landscape (keyword (:out m))
                                        (keyword (:in m))])
                          (rf/dispatch [::events/set-ui-states :selected-source
                                        (keyword (:out m))])
                          (rf/dispatch [::events/set-ui-states :selected-destination
                                        (keyword (:in m))])
                          (rf/dispatch [::tabs-ns/set-tab ::info :details-link]))}
            [:div.flex.flex-col
             [:div.flex.justify-between.text-indigo-600.items-end [:div (categories-map (:out m) (:out m))]
              [:div.text-2xl (:effect m)]]
             [:div.flex.gap-2
              [:div.text-gray-400 "influences "]
              [:div.text-teal-600 (categories-map (:in m) (:in m))]]
             #_[:div.mt-2 (:link-description m)]
             (when (pos? (count (:studies m)))
               [:div.flex.justify-between.mt-2.text-gray-600
                (if (> (count (:studies m)) 1)
                  [:p (str/capitalize (or (:agreement-between-studies m) "No")) " aggreement  between " (count (:studies m)) " studies."]
                  [:p "One study is referenced."]
                  )
                #_[:div (when-let [s (:agreement-between-studies m)]
                        (str/capitalize (or s " ")))]])]]))]])))

(defn landscape-elements []
  [tab-content {:class ["min-w-full"]}
   [:div.min-w-full.overflow-y-scroll {:style {:height 500}}
    [wvcl/list {:class [:-pl-10 :min-w-full]}
     (for [m (sort-by :key categories)]
       ^{:key (:key m)}
       [wvcl/list-item {:on-mouse-enter #(rf/dispatch [::events/set-hover-landscape (:key m)])
                        :on-click #(rf/dispatch [::tabs-ns/set-tab ::info :details])
                        :class ["hover:bg-gray-50" "hover:text-black"]}
        (:label m)])]]])

(defn infos []
  (let [tab-view    (subscribe [::tabs-ns/tab ::info])
        interaction (subscribe [::subs/ui-states-value :selected-landscape])]
    (fn []
      [:div
       ^{:key @tab-view}
       [tabs {:id    ::info
              :choices
              [{:id :summary :label "Summary"}
               {:id :interactions :label "Interactions"}
               {:id :details-link :label "Details"}]
              :class [:w-full]}
        [:div.min-w-full.flex-auto
         (case @tab-view
           :interactions [interactions-list]
           :summary      [details-summary]
           :details-link [details-links]
           [details-summary])]]])))

(defn landscape []
  [:div {:on-mouse-enter
         #(do (rf/dispatch [::tabs-ns/set-tab
                            :wyssacademy.visual-synthesis.views/info
                            :interactions])
              (rf/dispatch [::events/set-ui-states :selected-source nil])
              (rf/dispatch [::events/set-ui-states :selected-destination nil]))}
   [wyssacademy.visual-synthesis.landscape/view]])

(defn app []
  [:main.w-screen.min-h-screen.overflow-x-hidden.bg-neutral-100
   [header]
   [:div.px-5
    [:section
     [:div.flex]
     [:div.min-h-screen
      [:div.flex.items-strech.h-full.flex-wrap.justify-between
       [:div.mb-10 {:class ["w-auto"] :style {:min-height 600}} [landscape]]
       [:div.pr-2.flex-grow {:class ["md:basis-full" "lg:basis-1/2"
                                     "xl:basis-1/4" "xl:pl-4" "xl:w-full"]}
        [infos]]]]]]
   [footer]])

(comment
  (count @(subscribe [::subs/interactions]))
  @(subscribe [::subs/interactions-list-filtered])
  )
