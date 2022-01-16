(ns wyssacademy.visual-synthesis.core
  (:require
   [reagent.dom :as d]
   [wyssacademy.visual-synthesis.events :as events]
   [re-frame.core :as rf]
   [wyssacademy.visual-synthesis.views :refer (app)]))

(defn mount-app []
  (d/render [app] (.getElementById js/document "app")))

(defn ^:dev/after-load reload! []
  (mount-app))

(defn main []
  (rf/dispatch [::events/init-db])
  (mount-app))
