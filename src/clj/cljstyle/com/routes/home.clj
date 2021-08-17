(ns cljstyle.com.routes.home
  (:require
    [cljstyle.config :as config]
    [cljstyle.format.core :as format.core]
    [ctmx.core :as ctmx]
    [ctmx.render :as render]
    [hiccup.page :refer [html5]]))


(defn html-response
  [body]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body body})


(defn html5-response
  ([body]
   (html-response
     (html5
       [:head
        [:meta {:charset "UTF-8"}]
        [:meta {:name "viewport"
                :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]]
       [:body (render/walk-attrs body)]
       [:script {:src "https://unpkg.com/htmx.org@1.5.0"}]
       [:link {:href "https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" :rel "stylesheet"}]
       [:style
        "/* Left will inherit from right (so we don't need to duplicate code */\n.github-fork-ribbon {\n  /* The right and left lasses determine the side we attach our banner to */\n  position: absolute;\n\n  /* Add a bit of padding to give some substance outside the \"stitching\" */\n  padding: 2px 0;\n\n  /* Set the base colour */\n  background-color: #9b0024;\n\n  /* Set a gradient: transparent black at the top to almost-transparent black at the bottom */\n  background-image: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, 0.00)), to(rgba(0, 0, 0, 0.15)));\n  background-image: -webkit-linear-gradient(top, rgba(0, 0, 0, 0.00), rgba(0, 0, 0, 0.15));\n  background-image: -moz-linear-gradient(top, rgba(0, 0, 0, 0.00), rgba(0, 0, 0, 0.15));\n  background-image: -o-linear-gradient(top, rgba(0, 0, 0, 0.00), rgba(0, 0, 0, 0.15));\n  background-image: -ms-linear-gradient(top, rgba(0, 0, 0, 0.00), rgba(0, 0, 0, 0.15));\n  background-image: linear-gradient(top, rgba(0, 0, 0, 0.00), rgba(0, 0, 0, 0.15));\n  filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,StartColorStr='#000000', EndColorStr='#000000');\n\n  /* Add a drop shadow */\n  -webkit-box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.5);\n  box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.5);\n\n  z-index: 9999;\n}\n\n.github-fork-ribbon a {\n  /* Set the font */\n  font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n  font-size: 13px;\n  font-weight: 700;\n  color: white !important;\n\n  /* Set the text properties */\n  text-decoration: none;\n  text-shadow: 0 -1px rgba(0,0,0,0.5);\n  text-align: center;\n\n  /* Set the geometry. If you fiddle with these you'll also need to tweak the top and right values in #github-fork-ribbon. */\n  width: 200px;\n  line-height: 20px;\n\n  /* Set the layout properties */\n  display: inline-block;\n  padding: 2px 0;\n\n  /* Add \"stitching\" effect */\n  border-width: 1px 0;\n  border-style: dotted;\n  border-color: rgba(255,255,255,0.7);\n}\n\n.github-fork-ribbon-wrapper {\n  width: 150px;\n  height: 150px;\n  position: absolute;\n  overflow: hidden;\n  top: 0;\n}\n\n.github-fork-ribbon-wrapper.left {\n  left: 0;\n}\n\n.github-fork-ribbon-wrapper.right {\n  right: 0;\n}\n\n.github-fork-ribbon-wrapper.left-bottom {\n  position: fixed;\n  top: inherit;\n  bottom: 0;\n  left: 0;\n}\n\n.github-fork-ribbon-wrapper.right-bottom {\n  position: fixed;\n  top: inherit;\n  bottom: 0;\n  right: 0;\n}\n\n.github-fork-ribbon-wrapper.right .github-fork-ribbon {\n  top: 42px;\n  right: -43px;\n\n  /* Rotate the banner 45 degrees */\n  -webkit-transform: rotate(45deg);\n  -moz-transform: rotate(45deg);\n  -o-transform: rotate(45deg);\n  transform: rotate(45deg);\n}\n\n.github-fork-ribbon-wrapper.left .github-fork-ribbon {\n  /* Attach to the left of the page */\n  top: 42px;\n  left: -43px;\n\n  /* Rotate the banner -45 degrees */\n  -webkit-transform: rotate(-45deg);\n  -moz-transform: rotate(-45deg);\n  -o-transform: rotate(-45deg);\n  transform: rotate(-45deg);\n}\n\n\n.github-fork-ribbon-wrapper.left-bottom .github-fork-ribbon {\n  /* Attach to the left of the page */\n  top: 75px;\n  left: -64px;\n\n  /* Rotate the banner -45 degrees */\n  -webkit-transform: rotate(45deg);\n  -moz-transform: rotate(45deg);\n  -o-transform: rotate(45deg);\n  transform: rotate(45deg);\n}\n.github-fork-ribbon-wrapper.left-bottom .github-fork-ribbon a {\n  padding-left: 18px;\n}\n\n.github-fork-ribbon-wrapper.right-bottom .github-fork-ribbon {\n  /* Attach to the left of the page */\n  top: 89px;\n  left: -10px;\n\n  /* Rotate the banner -45 degrees */\n  -webkit-transform: rotate(-45deg);\n  -moz-transform: rotate(-45deg);\n  -o-transform: rotate(-45deg);\n  transform: rotate(-45deg);\n}\n.github-fork-ribbon-wrapper.right-bottom .github-fork-ribbon a {\n  padding-left: 15px;\n}"]))))


(ctmx/defcomponent ^:endpoint form [req input]
                   [:form#form-container
                    [:div {:class "text-center"}
                     [:button
                      {:class "bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                       :hx-post "form"
                       :hx-trigger "click"
                       :hx-target "#form-container"
                       :hx-swap "outerHTML"} "format"]]
                    [:div {:class "grid justify-center items-center grid-flow-col grid-cols-2 gap-5"}
                     [:div
                      [:div {:class "font-bold text-blue-500 text-center text-3xl mb-3"} "Input"]
                      [:textarea#input
                       {:class "form-textarea w-full h-screen border-blue-500 border rounded"
                        :name "input"
                        :placeholder "(  ns\n foo.bar.baz  \"some doc\"\n    (:require (foo.bar [abc :as abc]\n        def))\n    (:use foo.bar.qux)\n    (:import foo.bar.qux.Foo\n      ;; Need this for the thing\n      foo.bar.qux.Bar)\n    )\n\n(defn hello \"says hi\" (\n      [] (hello \"world\")\n  ) ([name]\n  ( println \"Hello,\" name  )\n  ))"}
                       input]]
                     [:div
                      [:div {:class "font-bold text-blue-500 text-center text-3xl mb-3"} "Output"]
                      [:textarea
                       {:class "form-textarea w-full h-screen border-blue-500 border rounded"
                        :placeholder "(ns foo.bar.baz\n  \"some doc\"\n  (:require\n    [foo.bar.abc :as abc]\n    [foo.bar.def]\n    [foo.bar.qux :refer :all])\n  (:import\n    (foo.bar.qux\n      ;; Need this for the thing\n      Bar\n      Foo)))\n\n\n(defn hello\n  \"says hi\"\n  ([] (hello \"world\"))\n  ([name]\n   (println \"Hello,\" name)))"}

                       (str (format.core/reformat-string input (:rules config/default-config)))]]]])


(defn link
  [text destination]
  [:a {:href destination :target "_" :class "text-blue-500 hover:opacity-75"} text])


(defn home-routes
  []
  (ctmx/make-routes
    "/"
    (fn [req]
      (html5-response

        [:div {:class "container mx-auto p-4"}
         [:div.github-fork-ribbon-wrapper.right
          [:div.github-fork-ribbon
           [:a {:href "https://github.com/SalvatoreTosti/cljstyle.com"} "Fork me on GitHub"]]]
         [:div {:class "font-bold text-blue-500 text-center my-5 text-5xl"} "cljstyle"]
         [:div
          {:class "text-center text-lg text-gray-700 mb-5"}
          [:div {:class "text-xl"} "big thanks to:"]
          [:ul
           [:li
            (link "greglook" "https://github.com/greglook")
            " for creating "
            (link "cljstyle" "https://github.com/greglook/cljstyle")]
           [:li (link "whamtet & co." "https://github.com/whamtet") " for putting together " (link "ctmx" "https://www.ctmx.info/")]]]
         (form req "")]))))
