(ns my-reagent-app.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [reagent.session :as session]
   [reitit.frontend :as reitit]
   [clerk.core :as clerk]
   [my-reagent-app.util :as util]
   [my-reagent-app.lib.cards :as cards]
   [accountant.core :as accountant]))

;; -------------------------
;; Page components

(defn home-page []
  (fn []
    [:span.main
     [:h1 "Welcome to my-reagent-app"]
     [:ul {:class "cards"}
      (for [item @cards/cards]
        ^{:key (:id item)}
        [:li {
              :class [(if (:visible item) "visible" "hidden")
                      (when (:found item) "found")]
              :on-click #(cards/click-on (:id item))
              } (:value item)])]]))


;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
