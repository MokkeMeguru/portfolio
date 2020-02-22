(ns portfolio.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [portfolio.events :as events]
   [portfolio.views :as views]
   [portfolio.config :as config]
   [portfolio.router :as router]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (router/start!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch-sync [::events/load-content "introduction"])
  (dev-setup)
  (mount-root))
