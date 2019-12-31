(ns portfolio.events
  (:require
   [re-frame.core :as re-frame]
   [portfolio.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]
   [ajax.edn :as ajaxedn]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))


(re-frame/reg-event-fx
 ::load-test-content
 (fn [{:keys [db]} _]
   {:db (assoc db :loaded true)
    :http-xhrio {:method :get
                 :uri "/contents/test.edn"
                 :timeout 2000
                 :response-format (ajaxedn/edn-response-format)
                 :on-success [::process-response]
                 :on-failure [::process-response]}}))

(re-frame/reg-event-db
 ::process-response
 (fn [db [_ response]]
   (-> db
       (assoc :loading? false)
       (assoc :data response))))
