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
         :on-mouse-enter #(rf/dispatch [::events/set-hover-landscape (keyword (:key data))])
         :on-mouse-leave #(rf/dispatch [::events/unset-hover-landscape])
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
  (let [links         (subscribe [::subs/interactions-landscape])
        selected-node (subscribe [::subs/ui-states-value :selected-landscape])]
    (fn []
      (let [ls       (if @selected-node
                       (filter (fn [{:keys [source target]}]
                                 (#{source target} (name (or @selected-node ""))))
                               @links)
                       @links)
            nodes    (-> (if @selected-node
                           (let [xf       (comp (map (fn [{:keys [source target]}]
                                                       [(keyword source) (keyword target)]))
                                                cat)
                                 node-ids (into #{} xf ls)]
                             (filterv (fn [{{key :key} :data}] (contains? node-ids key))
                                      db/categories-react-flow))
                           db/categories-react-flow)
                         (as-> $ (if-not (seq $) db/categories-react-flow $)))
            elements (into nodes ls)]
        ^{:key (str ls)}
        [:div
         {:style              {:width                 1280
                               :height                720
                               :background-image      "url(img/landscape-16-9.webp)"
                               :background-size       :contain
                               :background-repeat     :no-repeat
                               :background-color      "rgba(0,0,0,.20)"
                               :background-blend-mode (when @selected-node :darken)
                               :position              :relative}
          #_#_:on-mouse-leave #(rf/dispatch [::events/unset-hover-landscape])}
         #_[:div {:style {:width 1280 :height 720
                        :position :absolute
                        :background-blend-mode (when @selected-node :darken)}}
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
            :zoom-on-scroll  false
            :zoom-on-pinch   false
            :elements        elements}
           #_[controls]]]
         ]))))


(comment
  (into db/categories-react-flow @(subscribe [::subs/interactions-landscape]))
  (println Position)
  @(subscribe [::subs/interactions-landscape])
  )
