(ns wyssacademy.visual-synthesis.events
  (:require
   [ajax.core :as ajax]
   [ajax.edn]
   [camel-snake-kebab.core :as csk]
   [camel-snake-kebab.extras :as cske]
   [cljs-bean.core :refer (->clj)]
   [clojure.set :as set]
   [clojure.string :as str]
   [datascript.core :as d]
   [day8.re-frame.http-fx]
   [re-frame.core :as rf :refer (reg-event-fx reg-event-db)]
   [wyssacademy.visual-synthesis.db :as wvd :refer (default-db empty-ds)]
   [wyssacademy.visual-synthesis.dev :as dev]
   [wyssacademy.visual-synthesis.dev :as dev]))


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
   (tap> [event {:keys [file callback]} results])
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
 ::parse-interactions
 (fn [{db :db} _]
   {:db (update-in db [:data :interactions] #(mapv parse-interaction %))}))

(reg-event-fx
 ::init-db
 (fn [_ _]
   {:db wyssacademy.visual-synthesis.db/default-db
    :fx [[:dispatch [::retrieve-interactions]]]}))

(comment
  (rf/dispatch [::success]))
