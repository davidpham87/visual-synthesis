(ns wyssacademy.visual-synthesis.views
  (:require
   ["@material-tailwind/react/Button$default" :as button]
   ["@material-tailwind/react/Card$default" :as card]
   ["@material-tailwind/react/CardBody$default" :as card-body]
   ["@material-tailwind/react/Heading6$default" :as heading-6]
   [re-frame.core :as rf :refer (subscribe)]
   [wyssacademy.visual-synthesis.about :refer (about)]
   [wyssacademy.visual-synthesis.components.dropdown :as wvcd]
   [wyssacademy.visual-synthesis.components.list :as wvcl]
   [wyssacademy.visual-synthesis.components.navbar :refer (navbar)]
   [wyssacademy.visual-synthesis.components.tabs :as tabs-ns :refer
    (tabs tab-content)]
   [wyssacademy.visual-synthesis.components.typography :as typography]
   [wyssacademy.visual-synthesis.db :refer (categories categories-map)]
   [wyssacademy.visual-synthesis.details
    :refer (details-summary details-links)]
   [wyssacademy.visual-synthesis.events :as events]
   [wyssacademy.visual-synthesis.landscape :as wvl]
   [wyssacademy.visual-synthesis.subs :as subs]))

(defn header []
  [:div.mb-12
   [:div.fixed.z-40 {:class ["2xl:visible" "invisible"]}
    [:img.z-40 {:src "img/logo.webp"}]]
   [navbar {:event [::events/set-nav]}]])

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
             "Select a factor to start")]
        [:div.flex.gap-4.mb-4
         (into ^{:key @interaction}
               [wvcd/dropdown {:size :sm :color :teal :button-text "Factor"}]
               (xf :selected-landscape)
               categories-sorted)
         [:> button {:color :blue :on-click #(on-click {:key nil} :selected-landscape)} "Show all"]]]

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
             [typography/paragraph [:p.text-justify.pr-4.mt-1.mb-1 (:link-description m)]]
             #_(when (pos? (count (:studies m)))
                 [:div.flex.justify-between.mt-2.text-gray-600
                  (if (> (count (:studies m)) 1)
                    [:p (str/capitalize (or (:agreement-between-studies m) "No")) " aggreement  between " (count (:studies m)) " studies."]
                    [:p "One study is referenced."]
                    )
                  #_[:div (when-let [s (:agreement-between-studies m)]
                            (str/capitalize (or s " ")))]])
             ]]))]])))

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

(defn legends []
  (let [->label {-2 "strong negative effect"
                 -1 "moderate negative effect"
                 0 "neutral link"
                 1 "moderate positive effect"
                 2 "strong positive effect"}]
    [:> card {:class ["mb-4" "-mt-4"]}
     [:> card-body
      [:<>
       [typography/paragraph "Legends"]
       (into [:div.grid.grid-cols-3.gap-2]
             (for [[score color]
                   [[-2 "#f32c22"] [-1 "#ef8575"] [0 "#fff1f2"] [1 "#7bb526"] [2 "#a5f531"]]]
               [:div.flex.gap-2.items-center
                [:svg {:width 120, :height 26}
                 [:g {:stroke-width 4, :stroke color, :fill color}
                  [:path {:d "M5 5 l215 0", :stroke-dasharray "10,10"}]]]
                [typography/paragraph {:style {:font-size 12}}(->label score)]]))]]]))

(defn infos []
  (let [tab-view    (subscribe [::tabs-ns/tab ::info])]
    (fn []
      [:div.flex.flex-col
       ^{:key @tab-view}
       [tabs {:id    ::info
              :choices
              [{:id :summary :label "Summary"}
               {:id :interactions :label "Links"}
               {:id :details-link :label "Details"}]
              :class [:w-full]}
        [:div.min-w-full.flex-auto
         (case @tab-view
           :interactions [interactions-list]
           :summary      [details-summary]
           :details-link [details-links]
           [details-summary])]]
       [legends]])))

(defn landscape []
  [:div
   {:on-mouse-enter
    #(do (rf/dispatch [::tabs-ns/set-tab
                       :wyssacademy.visual-synthesis.views/info
                       :interactions])
         (rf/dispatch [::events/set-ui-states :selected-source nil])
         (rf/dispatch [::events/set-ui-states :selected-destination nil]))}
   [wyssacademy.visual-synthesis.landscape/view]])

(defn main-view []
  (let [nav-key (rf/subscribe [::subs/nav-key])]
    (fn []
      ^{:key @nav-key}
      (case @nav-key
        :about [about]
        [:div.min-h-screen
         [:div.flex.items-strech.h-full.flex-wrap.justify-between
          [:div.mb-10 {:class ["w-auto"] :style {:min-height 600}}
           [landscape]]
          [:div.pr-2.flex-grow {:class ["md:basis-full" "lg:basis-1/2"
                                        "xl:basis-1/4" "xl:pl-4" "xl:w-full"]}
           [infos]]]]))))

(defn app []
  [:main.w-screen.min-h-screen.overflow-x-hidden.bg-neutral-100
   [header]
   [:div.px-5
    [:section
     [:div.flex]
     [main-view]]]
   [footer]])

(comment
  3
  (count @(subscribe [::subs/interactions]))
  @(subscribe [::subs/interactions-list-filtered])
  )
