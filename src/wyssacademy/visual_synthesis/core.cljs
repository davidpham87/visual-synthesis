(ns wyssacademy.visual-synthesis.core
  (:require [reagent.dom :as d]))

(defn mount-app []
  (d/render [:div "Hello"]))

(defn main [])

(defn ^:dev/after-load reload []
  (mount-app))
