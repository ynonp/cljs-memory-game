(ns my-reagent-app.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [my-reagent-app.core-test]))

(doo-tests 'my-reagent-app.core-test)
