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
   [wyssacademy.visual-synthesis.db :refer (default-db empty-ds)]))


(defn xhrio-map [{:keys [method event data format uri]}]
  {:method     (or method :get)
   :uri        uri
   :format     (case format
                 :edn     (ajax.edn/edn-request-format)
                 :text    (ajax/text-request-format)
                 :transit (ajax/transit-request-format)
                 (ajax/json-request-format))
   :response-format
   (case format
     :edn     (ajax.edn/edn-response-format)
     :text    (ajax/text-request-format)
     :transit (ajax/transit-response-format)
     (ajax/json-response-format {:keywords? true}))
   :on-success
   [(keyword (namespace event)
             (str/join "-" [(name event) "success"])) data]
   :on-failure [::api-request-error event data]})

(reg-event-fx
 ::init-db
 (fn [_ _]
   {:db wyssacademy.visual-synthesis.db/default-db}))

(reg-event-fx
 ::api-request-error
 (fn [{:keys [db]} [_ event data results]]
   (assoc-in db [:errors event] {:data data
                                 :results results})))

(reg-event-fx
 ::retrieve-edn
 (fn [_ [event {:keys [uri file]}]]
   {:http-xhrio
    (doto (xhrio-map {:data {:file file} :event event :format :edn
                      :uri uri})
      println)}))

(reg-event-fx
 ::retrieve-edn-success
 (fn [{:keys [db]} [event {:keys [file]} results]]
   {:db (assoc-in db [:data file] results)}))

(reg-event-fx
 ::retrieve-interactions
 (fn [{db :db}]
   {:fx [[:dispatch [::retrieve-edn {:file :interactions
                                     :uri "data/interactions.edn"}]]]}))

(comment
  (rf/dispatch [::retrieve-interactions])
  )
