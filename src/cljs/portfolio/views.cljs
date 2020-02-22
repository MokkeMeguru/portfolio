(ns portfolio.views
  (:require
    [re-frame.core :as re-frame]
    [portfolio.subs :as subs]
    [reitit.frontend :as rf]
   [bulma-cljs.core :as b]
   [cljs.tools.reader.edn :as edn]
   [portfolio.router :as router]
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

(defn remove-class
  ([id]
   (toggle-class id "is-active"))
  ([id toggled-class]
   (let [el-classList (.-classList (.getElementById js/document id))]
     (if (.contains el-classList toggled-class)
       (.remove el-classList toggled-class)))))


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
  (let [content-id @(re-frame/subscribe [::subs/data-id])
        nav-contents @(re-frame/subscribe [::subs/nav-contents])]
   [:nav#nav_box.navbar.box
     [:div.navbar-brand
      [:a.navbar-item {:href "https://mokkemeguru.github.io/portfolio/resources/public/index.html"}
       [:img#logo {:src "https://avatars0.githubusercontent.com/u/30849444?s=400&u=75bde9345fbaf950cceec1d8fc4dc68eff83507a&v=4"}]]
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
          [:a.nitem.navbar-item
           {:on-click
            #(do
               (print "keyword "(keyword ":router" (get nav-contents content)))
               (print (rf/match-by-name router/router (keyword :portfolio.router (get nav-contents content))))
               (re-frame/dispatch-sync [::events/load-content (get nav-contents content)])
               (re-frame/dispatch [::events/navigate (keyword :portfolio.router (get nav-contents content))])
               (remove-class "main-navbar"))}
           (if (=  (get nav-contents content) content-id) [:p.has-text-link content] [:p content])])
        (keys nav-contents))]
      [nav-panel-end]
      ]]))



(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        data (re-frame/subscribe [::subs/data])
        found? (re-frame/subscribe [::subs/found?])]
    [:div
     [nav-panel]
     [:div
      (if @found?
        [:div.container
         (map
          (fn [content]
            (let [subtitle (-> content :subtitle)
                  description (-> content :description)
                  cont (-> content :content)
                  icon (-> content :icon)]
              ^{:key subtitle}
              [:div.columns.**is-desktop**
               [:div.column.is-1.**is-desktop**
                {:style {:background-color "gray"
                         :background-clip  "content-box"
                         :margin-top "1rem"}}]
               [:div.column.is-10.**is-desktop**>div.columns.**is-desktop**>div.column.is-8.is-offset-2>div.box
                (if icon
                  [:div.column>div.columns>div.column.is-paddingless-left>article.media
                   [:div.media-content [:h2.title.is-spaced subtitle]]
                   [:figure.media-right.image.is-48x48>img {:src  (str "img/" icon)}]]
                  [:h2.title.is-spaced subtitle])
                [:p description][:br]
                cont]]))
          (-> @data :contents))])
      ]]))
