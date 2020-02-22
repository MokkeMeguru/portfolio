(ns portfolio.router
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [re-frame.core :as re-frame]
            [portfolio.db :as db]
            [portfolio.events :as events]))


(def routes
  ["/"
   (conj
    (into [["" :introduction]]
          (vec (map (fn [v] [v (keyword v)]) (vals (:nav-contents db/default-db)))))
    [true :not-found])])

(defn set-page! [match]
  (re-frame/dispatch-sync [::events/set-page match]))

(def history
  (pushy/pushy set-page! (partial bidi/match-route routes)))

(defn- parse-url
  [url]
  (bidi/match-route routes url))

(defn start!
  []
  (pushy/start! history))

(def url-for (partial bidi/path-for routes))
