(ns wyssacademy.visual-synthesis.events
  (:require
   [ajax.core :as ajax]
   [ajax.edn]
   [camel-snake-kebab.core :as csk]
   [clojure.string :as str]
   [day8.re-frame.http-fx]
   [re-frame.core :as rf :refer (reg-event-fx)]
   [wyssacademy.visual-synthesis.db :as wvd]
   [wyssacademy.visual-synthesis.dev :as dev]
   [wyssacademy.visual-synthesis.components.tabs :as tabs-ns]))


(defn xhrio-map [{:keys [method event data format response-format uri]}]
  {:method     (or method :get)
   :uri        uri
   :format     (case (or format :text)
                 :edn     (ajax.edn/edn-request-format)
                 :text    (ajax/text-request-format)
                 :transit (ajax/transit-request-format)
                 :json    (ajax/json-request-format)
                 (ajax/text-request-format))
   :response-format
   (case (or response-format :text)
     :edn     (ajax.edn/edn-response-format)
     :text    (ajax/text-request-format)
     :transit (ajax/transit-response-format)
     :json    (ajax/json-response-format {:keywords? true})
     (ajax/text-request-format))
   :on-success
   [(keyword (namespace event)
             (str/join "-" [(name event) "success"])) data]
   :on-failure [::api-request-error event data]})

(reg-event-fx
 ::api-request-error
 (fn [{:keys [db]} [_ event data results]]
   (tap> [event data results])
   {:db (assoc-in db [:errors event] {:data data
                                      :results results})}))

(reg-event-fx
 ::retrieve-edn
 (fn [_ [event {:keys [uri file callback]}]]
   {:http-xhrio
    (xhrio-map {:data {:file file :callback callback} :event event
                :response-format :edn
                :uri uri})}))

(reg-event-fx
 ::retrieve-edn-success
 (fn [{:keys [db]} [event {:keys [file callback]} results]]
   {:db (assoc-in db [:data file] results)
    :fx [(when callback [:dispatch callback])]}))

(reg-event-fx
 ::success
 (fn [{db :db}] (println "Success")))

(reg-event-fx
 ::retrieve-interactions
 (fn [{db :db} _]
   {:fx [[:dispatch [::retrieve-edn {:file :interactions
                                     :uri "data/interactions.edn"
                                     :callback [::parse-interactions]}]]]}))

(defn parse-interaction [m]
  (-> m
      (update :out (fnil csk/->kebab-case-keyword (rand-nth wvd/categories-keys)))
      (update :in (fnil csk/->kebab-case-keyword (rand-nth wvd/categories-keys)))
      (update :effect (fnil identity (rand-nth ["--" "-" "+" "++"])))))

(reg-event-fx
 ::retrieve-studies
 (fn [{db :db} _]
   {:fx [[:dispatch [::retrieve-edn {:file :studies
                                     :uri "data/studies.edn"}]]]}))

(reg-event-fx
 ::parse-interactions
 (fn [{db :db} _]
   {:db (update-in db [:data :interactions]
                   #(->> %
                         (mapv parse-interaction)
                         (filterv (fn [m] (pos? (count (:studies m)))))))}))

(reg-event-fx
 ::init-db
 (fn [_ _]
   {:db wyssacademy.visual-synthesis.db/default-db
    :fx [[:dispatch [::retrieve-interactions]]
         [:dispatch [::retrieve-studies]]]}))

(reg-event-fx
 ::set-ui-states
 (fn [{db :db} [_ key value]]
   {:db (assoc-in db [:ui-states key] value)}))

(reg-event-fx
 ::set-hover-landscape
 (fn [_ [_ from to]]
   {:fx [[:dispatch [::set-ui-states :selected-landscape from]]
         #_[:dispatch [::set-ui-states :selected-source from]]
         (when to [:dispatch [::set-ui-states :selected-destination to]])]}))

(reg-event-fx
 ::unset-hover-landscape
 (fn [_ _]
   {:fx [[:dispatch [::set-ui-states :selected-landscape nil]]
         [:dispatch [::set-ui-states :selected-destination nil]]]}))

(rf/reg-event-db
 ::set-nav
 (fn [db [_ key]]
   (assoc db :nav-key key)))

(comment
  (rf/dispatch [::success]))
