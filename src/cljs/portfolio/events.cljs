(ns portfolio.events
  (:require
   [re-frame.core :as re-frame]
   [portfolio.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [day8.re-frame.http-fx]
   [reitit.frontend.controllers :as rfc]
   [reitit.frontend.easy :as rfe]
   [ajax.core :as ajax]
   [ajax.edn :as ajaxedn]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-fx
 ::navigate
 (fn [db [_ & route]]
   {::navigate! route}))

(re-frame/reg-event-db
 ::navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))


(re-frame/reg-fx
 ::navigate!
 (fn [route]
   (print route)
   (apply rfe/push-state route)))

(re-frame/reg-event-fx
 ::load-content
 (fn [{:keys [db]} [_ id]]
   {:db (assoc db :loaded true)
    :http-xhrio {:method :get
                 :uri (str "./contents/" id ".edn")
                 :timeout 2000
                 :response-format (ajaxedn/edn-response-format)
                 :on-success [::resource-get-success]
                 :on-failure [::resource-get-failed]}}))

(re-frame/reg-event-db
 ::resource-get-success
 (fn [db [_ response]]
   (-> db
       (assoc :loading? false)
       (assoc :found? true)
       (assoc :data-id (-> response :id))
       (assoc :data response))))

(re-frame/reg-event-db
 ::resource-get-failed
 (fn [db [_ response]]
   (-> db
       (assoc :loading? false)
       (assoc :found? false))))
