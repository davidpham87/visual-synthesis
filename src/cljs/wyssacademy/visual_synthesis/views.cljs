(ns wyssacademy.visual-synthesis.views
  (:require
   [re-frame.core :as rf :refer (subscribe)]
   [wyssacademy.visual-synthesis.components.list :as wvcl]
   [wyssacademy.visual-synthesis.components.navbar :refer (navbar)]
   [wyssacademy.visual-synthesis.components.tabs :as tabs-ns :refer (tabs tab-content)]
   [wyssacademy.visual-synthesis.db :refer (categories categories-map)]
   [wyssacademy.visual-synthesis.details :refer (details)]
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

(defn interactions-list [interactions]
  [tab-content {}
   [:div.overflow-y-scroll {:style {:height 500}}
    [wvcl/list {:class [:-pl-10]}
     (for [m interactions]
       ^{:key (str (:link-description m))}
       [wvcl/list-item {:class ["hover:bg-gray-50" "hover:text-black"]}
        [:div.flex.flex-col ;; .-mx-3.pr-3
         [:div.flex.justify-between.text-indigo-600.items-end [:div (categories-map (:out m) (:out m))]
          [:div.text-2xl (:effect m)]]
         [:div.flex.gap-2
          [:div.text-gray-400 "influences "]
          [:div.text-teal-600 (categories-map (:in m) (:in m))]]
         [:div.mt-2 (:link-description m)]]])]]])

(defn landscape-elements []
  [tab-content {:class ["min-w-full"]}
   [:div.min-w-full.overflow-y-scroll {:style {:height 500}}
    [wvcl/list {:class [:-pl-10 :min-w-full]}
     (for [m categories]
       ^{:key (:key m)}
       [wvcl/list-item {:class ["hover:bg-gray-50" "hover:text-black"]} (:label m)])]]])

(defn infos []
  (let [interactions (subscribe [::subs/interactions])
        tab-view (subscribe [::tabs-ns/tab ::info])]
    (fn []
      [:div.flex.flex-1 {:style {:flex-basis "100%"}}
       ^{:key @tab-view}
       [tabs {:id ::info
              :choices
              [{:id :landscape-elements :label "Landscape Elements"}
               {:id :interactions :label "Interactions"}]
              :class [:w-full]}
        [:div.min-w-full.flex-auto
         (case @tab-view
           :interactions [interactions-list @interactions]
           [landscape-elements])]]])))

(def images
  {:biodiversity
   {:src "img/biodiversity.png" :style {:top "40%" :right "-5%"}}
   :forest-fragment
   {:src "img/forest_fragment.png" :style {:top "50%" :left "10%"}}
   :old-growth-forest
   {:src "img/old_growth_forest.png"
    :style {:top "60%" :left "-8%"}}
   :rice-paddy {:src "img/rice_paddy.png"
                :style {:top "65%" :left "35%"}}
   :water-body  {:src "img/water_body.png"
                 :style {:top "56%" :left "50%"}}})

(defn landscape []
  [wyssacademy.visual-synthesis.landscape/view])

(defn app []
  [:main.w-screen.min-h-screen.overflow-x-hidden
   [header]
   [:div.md:px-10
    [:section
     [:div.min-h-screen
      [:div.flex.items-strech.w-full.gap-10.h-full.mb-10.lg:flex-nowrap.sm:flex-wrap
       [:div {:class ["w-full xl:min-w-96"]
              :style {:height 600}}
        [landscape]]
       [infos]]
      [details]]]]
   [footer]])
