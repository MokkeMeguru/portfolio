(ns portfolio.views
  (:require
   ;; [ajax.core :refer [GET]]
   [re-frame.core :as re-frame]
   [portfolio.subs :as subs]
   [bulma-cljs.core :as b]
   [cljs.tools.reader.edn :as edn]
   [portfolio.events :as events]
   [shadow.resource :as rc]))

(defn toggle-class
  ([id]
   (toggle-class id "is-active"))
  ([id toggled-class]
   (let [el-classList (.-classList (.getElementById js/document id))]
     (if (.contains el-classList toggled-class)
       (.remove el-classList toggled-class)
       (.add el-classList toggled-class)))))

(defn twitter-link []
  [:a.bd-tw-button.button
   {:data-social-network "Twitter"
    :data-social-action "tweet"
    :data-social-target "https://github.com/MokkeMeguru"
    :target "_blank"
    :href "https://twitter.com/intent/tweet?text=I visited his site. @MeguruMokke"}
   [:span.icon
    [:i.fab.fa-twitter]]
   [:span
    "Tweet" ]])

(defn nav-panel-end []
  [:div.navbar-end
   [:div.navbar-item
    [:div.field.is-grouped]
    [:p.control
     [twitter-link]]]])

(defn nav-panel []
  [:nav#nav_box.navbar.box
   [:div.navbar-brand
    [:a.navbar-item {:href "https://mokkemeguru.github.io/portfolio/resources/public/index.html"} [:img#logo {:src "https://avatars0.githubusercontent.com/u/30849444?s=400&u=75bde9345fbaf950cceec1d8fc4dc68eff83507a&v=4"} ]]
    [:a.navbar-burger.burger
     {:aria-label "menu"
      :role "button"
      :aria-expanded "false"
      :data-target "main-navbar"
      :on-click #(toggle-class "main-navbar")}
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]]]
   [:div#main-navbar.navbar-menu
    [b/navbar-start
     (map
      (fn [content] ^{:key content}
        [:a.nitem.navbar-item content])
          ["Introduction" "VF project" "About" "NLP & ML" "Web Dev" "Illust"])]
    [nav-panel-end]
    ]])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        data (re-frame/subscribe [::subs/data])]
    [:div
     [nav-panel]
     [:div
      
      [:div
       "Hello from " @name 
       ]
      [:button.button.is-primary {:on-click #(re-frame/dispatch-sync [::events/load-test-content])} "get sample message"]
      [:div "sample data" @data]
      ]]))
