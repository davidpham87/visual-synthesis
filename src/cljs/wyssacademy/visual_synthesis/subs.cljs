(ns wyssacademy.visual-synthesis.subs
  (:require
   [wyssacademy.visual-synthesis.dev :as dev]
   [re-frame.core :refer (reg-sub)]
   [re-frame.core :as rf]))

(reg-sub
 ::user-input
 (fn [db _] (:user-input db)))

(reg-sub
 ::ui-states
 (fn [db _] (:ui-states db)))

(reg-sub
 ::ui-states-value
 :<- [::ui-states]
 (fn [m [_ k]]
   (do (get m k))))

(reg-sub
 ::ds
 (fn [db _] (:ds db)))

(reg-sub
 ::data
 (fn [db _] (:data db {})))

(reg-sub
 ::studies
 :<- [::data]
 (fn [m _]
   (into {} (map (fn [m] [(:code m) m])) (:studies m))))

(reg-sub
 ::interactions
 :<- [::data]
 (fn [m _]
   (:interactions m)))

(reg-sub
 ::interactions-list-filtered
 :<- [::interactions]
 :<- [::ui-states-value :selected-source]
 :<- [::ui-states-value :selected-destination]
 :<- [::ui-states-value :selected-landscape]
 (fn [[ms s d l]]
   (tap> {:ms ms :l l})
   (cond
     s (filterv (fn [{:keys [out]}] (= out s)) ms)
     d (filterv (fn [{:keys [in]}] (= in d)) ms)
     l (filterv (fn [{:keys [in out]}] (or (= in l) (= out l))) ms)
     :else (vec ms))))

(reg-sub
 ::interactions-by-origin
 :<- [::interactions]
 (fn [ms [_ ks]]
   (reduce-kv (fn [m k ms] (assoc m k (mapv #(select-keys % ks) ms)))
              {} (group-by :out ms))))

(reg-sub
 ::interactions-by-destination
 :<- [::interactions]
 (fn [ms [_ ks]]
   (reduce-kv (fn [m k ms] (assoc m k (mapv #(select-keys % ks) ms)))
              {} (group-by :in ms))))

(reg-sub
 ::interactions-by-link
 :<- [::interactions]
 (fn [ms [_ ks]]
   (reduce-kv (fn [m k ms] (assoc m k (mapv #(select-keys % ks) ms)))
              {} (group-by (juxt :out :in) ms))))

(defn kw->str [id]
  (let [id (or id "unfounded")]
    (if (qualified-keyword? id)
      (str (namespace id) "/" (name id))
      (name id))))

(defn ids->edge [id1 id2 effect]
  {:id (str "e-" (kw->str id1) "+" (kw->str id2))
   :source (kw->str id1)
   :target (kw->str id2)
   :animated true
   ;; "arrowHeadType" "arrowclosed"
   :style {:stroke-width 3
           :stroke (-> {2 "#a5f531"
                        1 "#7bb526"
                        0 "#fff1f2"
                        -1 "#ef8575"
                        -2 "#f32c22"}
                       (get effect "#fff1f2"))}})

(reg-sub
 ::interactions-landscape
 :<- [::interactions]
 (fn [ms]
   (mapv (fn [m] (ids->edge (:out m) (:in m) (:effect m))) ms)))

(comment
  (dev/log [::interactions])
  (dev/log [::studies])
  (rf/clear-subscription-cache!)
  (dev/log [::interactions-by-origin [:in]])
  )
