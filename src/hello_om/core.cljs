(ns hello-om.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-bootstrap.button :as b]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"
                      :foo 2
                      :lst [{:class "lst-1" :data "Hello, World!"}
                            {:class "lst-2" :data "Hello, Zooo!"}]
                      }))


(defn get-new-state [num]
  (if (= (mod num 2) 1)
    {:class "lst-1" :data "Hello, World!"}
    {:class "lst-2" :data "Hello, Zooo!"}))

(defn click-inc []
  (swap! app-state 
         assoc :lst 
         (into (:lst @app-state) [(get-new-state (:foo @app-state))]))
  (swap! app-state assoc :foo (+ 1 (:foo @app-state)))) 

;; Render input form
(om/root
 (fn [app owner]
   (om/component
    (dom/div #js {:className "my-color-box"}              
             (dom/h4 nil "Clicks count: ")
             (dom/h1 nil (:foo app))
             (b/button {:bs-style "success"
                        :on-click click-inc} (:text app)))))
 app-state
 {:target (. js/document (getElementById "app"))})


;; Render item list
(om/root
 (fn [app owner]
   (om/component
    (apply dom/ul #js {:className "actions"}
           (map (fn [data] 
                  (dom/li #js {:className (:class data)} (:data data))) 
                (:lst app)))))
 app-state
 {:target (. js/document (getElementById "some-list"))})
