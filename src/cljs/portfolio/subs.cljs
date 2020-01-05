(ns portfolio.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::nav-contents
 (fn [db]
   (:nav-contents db)))


(re-frame/reg-sub
 ::loaded
 (fn [db]
   (:loaded db)))

(re-frame/reg-sub
 ::data-id
 (fn [db]
   (:data-id db)))


(re-frame/reg-sub
 ::data
 (fn [db]
   (:data db)))

(re-frame/reg-sub
 ::found?
 (fn [db]
   (:found? db)))
