(ns cljstyle.com.routes.home
  (:require
    [ctmx.core :as ctmx]
    [ctmx.render :as render]
    [hiccup.page :refer [html5]]
    [cljstyle.format.core :as format.core]
    [cljstyle.config :as config]
    ))

(defn html-response [body]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body body})

(defn html5-response
  ([body]
   (html-response
     (html5
       [:head
        [:meta {:name "viewport"
                :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]]
       [:body (render/walk-attrs body)]
       [:script {:src "https://unpkg.com/htmx.org@1.5.0"}]
       [:link {:href "https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" :rel "stylesheet"}]
       ))))

(ctmx/defcomponent ^:endpoint root [req ^:int num-clicks]
                   [:div.m-3 {:hx-post "root"
                              :hx-swap "outerHTML"
                              :hx-vals {:num-clicks (inc num-clicks)}}
                    "You have clicked me " num-clicks " times."])

(ctmx/defcomponent ^:endpoint format [req ^:string text]
                   [:textarea#hello
                    {:class "form-textarea w-full h-screen border-gray-700 border"}
                    (str (format.core/reformat-string text (:rules config/default-config)))])

(defn home-routes []
  (ctmx/make-routes
    "/"
    (fn [req]
      (html5-response
        [:div {:class "container mx-auto p-4"}
         [:div {:class "font-bold text-gray-700 text-center my-5 text-5xl"} "cljstyle"]
         [:div {:class "grid justify-center items-center grid-flow-col grid-cols-2 gap-5"}
          [:div
           [:div {:class "font-bold text-gray-700 text-center text-3xl mb-3"} "Input"]
           [:textarea {:class "form-textarea w-full h-screen border-gray-700 border" :name "text" :hx-patch "format" :hx-target "#hello" :hx-swap "outerHTML"}]]
          [:div
           [:div {:class "font-bold text-gray-700 text-center text-3xl mb-3"} "Output"]
           (format req "")
           ]
          ]
         ]
        ))))
