(ns portfolio.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:html {:height "100%"}]
  [:body {:font-size "1rem"
          :font-family "BlinkMacSystemFont,-apple-system,\"Segoe UI\",Roboto,Oxygen,Ubuntu,Cantarell,\"Fira Sans\",\"Droid Sans\",\"Helvetica Neue\",Helvetica,Arial,sans-serif",
          :line-height "1.25"
          :margin 0
          :color "#7a7a7a"
          :background-image "url(\"../img/dot-grid.png\")"
          :height "100%"}
   [:#logo {:max-height "5rem"
            :padding "0.5rem"}]
   [:#nav_panel {:align-items "stretch"
                 :display "flex"
                 :margin-left "1rem"}]
   [:#nav_box {:padding 0}]
   [:.nitem {:font-size "1.5rem"}]]
  [:.is-paddingless-left {:padding-left 0}]
  [:p {:padding-top "0.75rem" }])
