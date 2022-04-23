(ns wyssacademy.visual-synthesis.components.tabs
  (:require
   [re-frame.core :as rf]
   [wyssacademy.visual-synthesis.utils :refer (styles->components-litteral defc)]))

(rf/reg-event-db
 ::set-tab
 (fn [db [_ path value]]
   (assoc-in db (into [::tab] (if (vector? path) path [path])) value)))

(rf/reg-sub
 ::tab-registry
 (fn [db _] (::tab db {})))

(rf/reg-sub
 ::tab
 :<- [::tab-registry]
 (fn [m [_ path]]
   (get-in m (if (vector? path) path [path]))))

(def styles
  {:tab {:comp :div
         :class ["relative" "flex" "flex-col" "items-center" "justify-center"
                 "bg-white" "rounded-lg" "shadow-md" "w-full" "p-4" "my-8"]}
   :tab-list {:comp :ul
              :class ["w-full" "rounded-lg" "p-4" "mx-5" "flex" "justify-start"
                      "-mt-12" "mb-6" "list-none"]}})

(declare tab)
(declare tab-list)

(styles->components-litteral
 {:tab {:comp :div
        :class ["relative" "flex" "flex-col" "items-center" "justify-center"
                "bg-white" "rounded-lg" "shadow-md" "w-full" "p-4" "my-8"]}
  :tab-list {:comp :ul
             :class ["w-full" "rounded-lg" "p-4" "mx-5" "flex" "justify-start"
                     "-mt-12" "mb-6" "list-none"]}})

(defc tab-content [m & children]
  [:div (merge-with into {:class ["relative" "flex" "flex-col" "min-w-0" "break-words" "w-full" "px-4" "pb-4"]} m)
   (into [:div {:class ["flex-auto" "font-light" "leading-normal"]}]
         children)])

(defc tab-item [{:keys [active?] :as m} & children]
  [:li.text-center
   (into
    [:a (merge-with into
                    {:class
                     (cond-> ["flex" "items-center" "justify-center" "gap-1" "rounded-lg"
                              "text-sm" "font-medium" "py-4" "px-6" "leading-normal"
                              "text-white" "transition-all" "duration-300"]
                       active? (into ["bg-white" "bg-opacity-20"])
                       true identity)}
                    (merge {:role :tablist} (dissoc m :active?)))]
    children)])

(defn tabs [{:keys [choices id]} & children]
  (let [active (rf/subscribe [::tab id])]
    (fn [{:keys [choices id]}]
      (into
       [tab
        ^{:key @active}
        [tab-list {:class ["bg-teal-700" "shadow-lg-teal"]}
         (into [:<>]
               (for [{:keys [key value label] :as c} choices
                     :let [tab-key (or (:id c) key value)]]
                 ^{:key [tab-key]}
                 [tab-item {:active? (= tab-key @active)
                            :on-click #(rf/dispatch [::set-tab id tab-key])}
                  label]))]]
       children))))

(comment
  (tab-item {:active? true} "Hello")
  (tab-content {} "Hello")
  (tab-content "Hello")
  @(rf/subscribe [::tab-registry])
  @(rf/subscribe [::tab :wyssacademy.visual-synthesis.views/info])

  (merge-with into {:a [2 3] :b 3} {:a [3 4] :c 34}))
