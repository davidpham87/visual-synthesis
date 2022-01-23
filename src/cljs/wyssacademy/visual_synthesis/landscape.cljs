(ns wyssacademy.visual-synthesis.landscape
  (:require
   [cljs-bean.core :refer (->clj)]
   ["react-flow-renderer" :default ReactFlow :refer [Background Controls ReactFlowProvider Handle Position]]
   [re-frame.core :refer (subscribe)]
   [reagent.core :as r]
   [wyssacademy.visual-synthesis.db :as db]
   [wyssacademy.visual-synthesis.subs :as subs]))

(def react-flow-pro (r/adapt-react-class ReactFlowProvider))
(def react-flow (r/adapt-react-class ReactFlow))
(def background (r/adapt-react-class Background))
(def controls (r/adapt-react-class Controls))
(def handle (r/adapt-react-class Handle))

(defn view []
  (let [links (subscribe [::subs/interactions-landscape])]
    (fn []
      ^{:key (str @links)}
      [:div {:style {:zoom 1.2
                     :width 1027 :height 500
                     :background-image "url(img/empty_lanscape.png)"}}
       [react-flow-pro
        [react-flow
         {:nodes-draggable false
          :node-types
          #js {"image"
               (r/reactify-component
                (fn [props]
                  (println props)
                  (let [data (->clj (:data props))]
                    [:div {:style {:padding 0 :margin 0}}
                     [handle {:type :target
                              :id "top"
                              :position :bottom}]
                     [:img {:src (:src data) :style {:padding 0
                                                     :zoom (:zoom data)}}]
                     [handle {:type :source
                              :id "a"
                              :position :top}]])))}
          :elements (into db/categories-react-flow @links)}
         [controls]]]])))


(comment
  (into db/categories-react-flow @(subscribe [::subs/interactions-landscape]))
  (println Position)
  )
