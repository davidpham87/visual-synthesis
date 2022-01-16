(ns wyssacademy.visual-synthesis.subs
  (:require
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
