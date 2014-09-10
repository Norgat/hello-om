(ns hello-om.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-bootstrap.button :as b]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"
                      :foo 0
                      :lst [["foo1" "bar1" "moo1"]
                            ["foo2" "bar2" "moo2"]
                            ["foo3" "bar3" "moo3"]
                            ["foo4" "bar4" "moo4"]]}))

(defn click-inc []
  (swap! app-state assoc :foo (+ 1 (:foo @app-state)))) 

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


(om/root
 (fn [app owner]
   (om/component
    (apply dom/ul #js {:className "actions"}
           (map (fn [text] (dom/li nil text)) 
                (nth (:lst app) (mod (:foo app) 4))))))
 app-state
 {:target (. js/document (getElementById "some-list"))})
