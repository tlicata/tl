(ns tl.pages.home
  (:use tl.pages.global))

(defn home-page
  [request]
  (page request
    {:title "Tim's Online World"
     :body
     [:div.blurb
      [:ul
       [:li "server-port: " (:server-port request)]
       [:li "server-name: " (:server-name request)]
       [:li "remote-addr: " (:remote-addr request)]
       [:li "uri: " (:uri request)]
       [:li "query-string: " (:query-string request)]
       [:li "scheme: " (:scheme request)]
       [:li "request-method: " (:request-method request)]
       [:li "headers: " (:headers request)]
       [:li "content-type: " (:content-type request)]
       [:li "character-encoding: " (:character-encoding request)]
       [:li "body: " (:body request)]
       [:li "appengine/user-info: " (:appengine/user-info request)]]]}))
