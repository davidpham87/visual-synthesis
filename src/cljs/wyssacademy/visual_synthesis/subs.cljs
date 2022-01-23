(ns wyssacademy.visual-synthesis.subs
  (:require
   [wyssacademy.visual-synthesis.dev :as dev]
   [re-frame.core :refer (reg-sub)]))

(reg-sub
 ::user-input
 (fn [db _] (:user-input db)))

(reg-sub
 ::ui-states
 (fn [db _] (:ui-states db)))

(reg-sub
 ::ds
 (fn [db _] (:ds db)))

(reg-sub
 ::data
 (fn [db _] (:data db {})))

(reg-sub
 ::interactions
 :<- [::data]
 (fn [m _] (:interactions m)))


(comment
  (dev/log [::interactions])
  )
