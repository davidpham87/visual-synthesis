(ns wyssacademy.visual-synthesis.components.browser
  (:require
   ["bowser" :as bowser]
   [camel-snake-kebab.core :as csk]
   [re-frame.core :as rf :refer (reg-event-fx reg-fx)]))



(rf/reg-event-fx
 ::set-browser
 (fn [{db :db} [_ browser]]
   {:db (assoc db ::browser browser)}))

(rf/reg-sub
 ::browser
 (fn [db] (::browser db)))

(rf/reg-sub
 ::firefox?
 :<- [::browser]
 (fn [browser]
   (= :firefox browser)))

(defn get-browser! []
  (let [b (bowser/getParser (.. js/window -navigator -userAgent))]
    (csk/->kebab-case-keyword (.getBrowserName b))))

(defn set-browser! []
  (rf/dispatch [::set-browser (get-browser!)]))

(rf/reg-fx
 ::set-browser
 set-browser!)

(comment
  (set-browser!)
  @(rf/subscribe [::browser])

  )
