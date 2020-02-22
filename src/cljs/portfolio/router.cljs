(ns portfolio.router
  (:require [re-frame.core :as re-frame]
            [portfolio.db :as db]
            [reitit.frontend :as rf]
            [portfolio.events :as events]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rcs]))

(defn href
  "Return relative url for given route. Url can be used in HTML links."
  ([k]
   (href k nil nil))
  ([k params]
   (href k params nil))
  ([k params query]
   (rfe/href k params query)))

(def routes
  ["/"
   [""
    {:name ::home
     :link-text "HOME"}]
   ["introduction"
    {:name ::introduction
     :link-text "introduction"}]
   ["vf-project"
    {:name ::vf-project}]
   ["nlp-ml"
    {:name ::nlp-ml}]
   ["web-dev"
    {:name ::web-dev}]
   ["illust"
    {:name ::illust}]
   ["others"
    {:name ::others}]])

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch-sync [::events/navigated new-match])))

(def router
  (rf/router
   routes
   {:data {:coercion rcs/coercion}}))


(defn init-routes! []
  (rfe/start!
   router
   on-navigate
   {:use-fragment true}))
