(ns cljstyle.com.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [cljstyle.com.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[cljstyle.com started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[cljstyle.com has shut down successfully]=-"))
   :middleware wrap-dev})
