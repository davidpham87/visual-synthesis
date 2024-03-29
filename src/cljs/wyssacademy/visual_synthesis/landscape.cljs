(ns wyssacademy.visual-synthesis.landscape
  (:require
   ["react-flow-renderer" :default ReactFlow :refer [Background Controls ReactFlowProvider Handle]]
   [cljs-bean.core :refer (->clj)]
   [re-frame.core :refer (subscribe)]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [wyssacademy.visual-synthesis.components.browser :as browser]
   [wyssacademy.visual-synthesis.components.typography :as typography]
   [wyssacademy.visual-synthesis.db :as db]
   [wyssacademy.visual-synthesis.events :as events]
   [wyssacademy.visual-synthesis.subs :as subs]))

(def react-flow-pro (r/adapt-react-class ReactFlowProvider))
(def react-flow (r/adapt-react-class ReactFlow))
(def background (r/adapt-react-class Background))
(def controls (r/adapt-react-class Controls))
(def handle (r/adapt-react-class Handle))

(defn landscape-component [data selected-node firefox?]
  [:div {:style (cond-> {:padding 0 :margin 0}
                  #_#_firefox? (merge {:transform (str "scale(" (:zoom data)")")
                                   :transform-origin "0 0"}))
         :class (into [:drop-shadow-xl]
                      (when (= (keyword (:key data)) selected-node)
                        ["border-2" "rounded-full" "border-yellow-200"]))
         :on-mouse-enter #(rf/dispatch [::events/set-hover-landscape (keyword (:key data))])
         :on-click #(do (rf/dispatch [::events/set-ui-states :selected-landscape
                                      (keyword (:key data))])
                        (rf/dispatch [::events/set-ui-states :hover? false]))}
   (when selected-node
     [handle {:type :target
              :id "top"
              :position :left}])
   [:img.drop-shadow-xl
    {:src (:src data)
     #_#_:style (when-not firefox? {:zoom (:zoom data)})}]
   (when selected-node
     [handle {:type :source
              :id "a"
              :position :right}])])

(defn view []
  (let [links (subscribe [::subs/interactions-landscape])

        selected-node
        (subscribe [::subs/ui-states-value :selected-landscape])

        selected-destination
        (subscribe [::subs/ui-states-value :selected-destination])

        firefox?
        (subscribe [::browser/firefox?])]

    (fn []
      (let [ls (cond
                 (and @selected-node @selected-destination)
                 (filter (fn [{:keys [source target]}]
                           (and (= source (name (or @selected-node "")))
                                (= target (name (or @selected-destination "")))))
                         @links)

                 @selected-node
                 (filter (fn [{:keys [source target]}]
                           (#{source target} (name (or @selected-node ""))))
                         @links)
                 :else [])

            nodes
            (-> (if @selected-node
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
         {:on-mouse-enter #(rf/dispatch [::events/set-ui-states :hover? true])
          :on-mouse-leave #(rf/dispatch [::events/set-ui-states :hover? false])
          :on-click (fn []
                      (when @selected-node (rf/dispatch [::events/unset-hover-landscape])))
          :style
          {:width                 1280
           :height                960
           :background-image      "url(img/background-landscape-4-3.webp)"
           :background-size       :contain
           ;; :background-repeat     :no-repeat
           :background-color      "rgba(0,0,0,0.40)"
           :background-blend-mode (when @selected-node :darken)
           :position              :relative}}
         [:div
          (when-let [data (db/categories-map-data @selected-node)]
            [:div.pt-2.px-4.text-white.bg-teal-700.rounded-3xl
             {:class []
              :style
              {:position :absolute
               :left (get-in data [:position :x])
               :top (- (get-in data [:position :y]) 50)
               :text-align :center
               :z-index 999
               :transform (str "scale(" (:zoom data )")")}}
             [typography/h6
              (or (wyssacademy.visual-synthesis.db/categories-map @selected-node)
                  "Select an factor to start")]])]
         [react-flow-pro
          [react-flow
           {:nodes-draggable false
            :node-types
            #js
            {"image"
             (r/reactify-component
              (fn [props]
                (let [data (->clj (:data props))]
                  [landscape-component data @selected-node @firefox?])))}
            :zoom-on-scroll  false
            :zoom-on-pinch   false
            :elements        elements}
           #_[controls]]]]))))


(comment
  (into db/categories-react-flow @(subscribe [::subs/interactions-landscape]))
  ;; (println Position)
  @(subscribe [::subs/interactions-landscape])
  )
