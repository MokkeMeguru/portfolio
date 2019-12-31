(ns portfolio.views
  (:require
   [re-frame.core :as re-frame]
   [portfolio.subs :as subs]
   [bulma-cljs.core :as b]
   ))

(defn nav-panel []
  [:nav#nav_box.navbar.box
   [:div#nav_panel.container
    [:div.navbar-brand
     [:a.navbar-item {:href "https://mokkemeguru.github.io/portfolio/resources/public/index.html"} [:img#logo {:src "https://avatars0.githubusercontent.com/u/30849444?s=400&u=75bde9345fbaf950cceec1d8fc4dc68eff83507a&v=4"} ]]]
    [:a.navbar-burger.burger
     {:aria-label "menu"
      :role "button"
      :aria-expanded "false"
      :data-target "mainnavbar"}
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]]
    [:div#mainnavbar.navbar-menu
     [b/navbar-start
      [:a.nitem.navbar-item "Introduction"]
      [:div.nitem.navbar-item.has-dropdown.is-hoverable
       [:a.navbar-link"VF project"]
       [:div.navbar-dropdown
        [:a.navbar-item "About"]]]
      [:div.nitem.navbar-item.has-dropdown.is-hoverable
       [:a.navbar-link"NLP & ML"]
       [:div.navbar-dropdown
        [:a.navbar-item "About"]]]
      [:div.nitem.navbar-item.has-dropdown.is-hoverable
       [:a.navbar-link "Web Dev"]
       [:div.navbar-dropdown
        [:a.navbar-item "About"]]]
      [:a.nitem.navbar-item "Illust"]]
     ]]])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [nav-panel]
     [:div
      [:div "Hello from " @name]]]))
