(ns wyssacademy.visual-synthesis.views
  (:require
   [re-frame.core :as rf :refer (dispatch subscribe)]
   ["@material-tailwind/react/Card$default" :as card]
   ["@material-tailwind/react/CardHeader$default" :as card-header]
   ["@material-tailwind/react/CardBody$default" :as card-body]
   ["@material-tailwind/react/Button$default" :as button]))

(defn header []
  [:div.fixed
   [:img {:src "img/logo.webp"}]])

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

(defn infos []
  [:div.w-full.mx-5.px-5
   [:> card
    [:> card-header
     [:h2.text-3xl "Information"]]
    [:> card-body
     [:div "Info"]
     [:> button "Info Button"]]]])

(defn details []
  [:div.w-full.mx-5.px-5
   [:> card
    [:> card-header {:size :sm :color :cyan}
     [:h2.text-3xl "Detail"]]
    [:> card-body
     [:div "Details"]
     [:> button {:color :red} "Details"]]]])

(def images
  {:biodiversity
   {:src "img/biodiversity.png" :style {:top "40%" :right "-5%"}}
   :forest-fragment
   {:src "img/forest_fragment.png" :style {:top "50%" :left "10%"}}
   :old-growth-forest {:src "img/old_growth_forest.png"}
   :rice-paddy {:src "img/rice_paddy.png"
                :style {:top "65%" :left "35%"}}
   :water-body  {:src "img/water_body.png"
                 :style {:top "56%" :left "50%"}}})

(defn landscape []
  [:div.relative.bg-cover.bg-center {:style {:width 1027 :height 500
                                             :background-image "url(img/empty_lanscape.png)"}}
   [:img.absolute (:biodiversity images)]
   [:img.absolute (:forest-fragment images)]
   [:img.absolute (:rice-paddy images)]
   [:img.absolute (:water-body images)]])

(defn app []
  [:main.w-screen.min-h-screen.overflow-x-hidden
   [header]
   [:div {:style {:height 154}}]
   [:section "Intro"]
   [:section "Visual Synthesis"]
   [:div..min-h-screen
    [:div.flex.w-screen
     [:div.min-h-full
      [landscape]]
     [:div.flex.flex-col.justify-around.items-center.w-full.flex-1
      [infos]
      [details]]]]
   [footer]])
