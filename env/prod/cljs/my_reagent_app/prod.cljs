(ns my-reagent-app.prod
  (:require [my-reagent-app.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
