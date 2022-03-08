(ns wyssacademy.visual-synthesis.landscape
  (:require
   [cljs-bean.core :refer (->clj)]
   ["react-flow-renderer" :default ReactFlow :refer [Background Controls ReactFlowProvider Handle Position]]
   [re-frame.core :refer (subscribe)]
   [reagent.core :as r]
   [wyssacademy.visual-synthesis.events :as events]
   [wyssacademy.visual-synthesis.db :as db]
   [wyssacademy.visual-synthesis.subs :as subs]
   [re-frame.core :as rf]))

(def react-flow-pro (r/adapt-react-class ReactFlowProvider))
(def react-flow (r/adapt-react-class ReactFlow))
(def background (r/adapt-react-class Background))
(def controls (r/adapt-react-class Controls))
(def handle (r/adapt-react-class Handle))

(defn landscape-component [data]
  [:div {:style {:padding 0 :margin 0}
         :on-click #(rf/dispatch [::events/set-ui-states :selected-landscape
                                  (keyword (:key data))])}
   [handle {:type :target
            :id "top"
            :position :bottom}]
   [:img {:src (:src data) :style {:zoom (:zoom data)}}]
   [handle {:type :source
            :id "a"
            :position :top}]])

(defn view []
  (let [links (subscribe [::subs/interactions-landscape])]
    (fn []
      ^{:key (str @links)}
      [:div
       {:style {:width 960 :height 680
                :background-image "url(img/empty_land.png)"
                :background-size :contain
                :background-repeat :no-repeat
                :position :relative}}
       [:div {:style {:width 960 :height 680 :position :absolute}}
        [:img {:src "img/empty_land_text.png"}]]
       [react-flow-pro
        [react-flow
         {:nodes-draggable false
          :node-types
          #js
          {"image"
           (r/reactify-component
            (fn [props]
              (println props)
              (let [data (->clj (:data props))]
                [landscape-component data])))}
          :zoom-on-scroll false
          :zoom-on-pinch false
          :elements (into db/categories-react-flow @links)}
         #_[controls]]]
       ])))


(comment
  (into db/categories-react-flow @(subscribe [::subs/interactions-landscape]))
  (println Position)
  )
