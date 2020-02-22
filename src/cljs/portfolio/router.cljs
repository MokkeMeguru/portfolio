(ns portfolio.router
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [re-frame.core :as re-frame]
            [portfolio.db :as db]))

(def state (atom {}))



(def routes
  ["/"
   (merge
    {"" :introduction}
    (into (sorted-map) (map (fn [v] [v (keyword v)]) (vals (:nav-contents db/default-db)))))])

(defn set-page! [match]
  (re-frame/dispatch-sync [::set-page match]))

(def history
  (pushy/pushy set-page! (partial bidi/match-route routes)))

(defn- parse-url
  [url]
  (bidi/match-route routes url))

(defn start!
  []
  (pushy/start! history))

(def url-for (partial bidi/path-for routes))
