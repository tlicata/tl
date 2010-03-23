(ns tl.pages.contact
  (:use tl.pages.global))

(defn contact
  [request]
  (page
    request
    {:title "Contact Tim"
     :body
     [:div
      [:div.blurb
       [:h1 "Email"]
       [:p "The best way to get ahold of me is through email.  tim [at] [this domain]."]]]}))
