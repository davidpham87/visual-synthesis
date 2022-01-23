(ns wyssacademy.visual-synthesis.dev
  (:require
   [cljs.pprint]
   [re-frame.core :as rf]
   [re-frame.db]))

(defn display! [x]
  (tap> x)
  (cljs.pprint/pprint x)
  x)

(defn log-sub [sub] (display! @(rf/subscribe sub)))
(def log log-sub)

(defn log-path [path]
  (if (nil? path)
    (display! @re-frame.db/app-db)
    (->> [path]
         (if (vector? path) path)
         (get-in @re-frame.db/app-db)
         display!)))
(def lp log-path)

(defn log-keys [path]
  (if (nil? path)
    (-> @re-frame.db/app-db keys display!)
    (->> (if (vector? path) path [path])
         (get-in @re-frame.db/app-db)
         keys
         vec
         display!)))
(def lk log-keys)
