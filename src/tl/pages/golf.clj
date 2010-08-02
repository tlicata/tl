(ns tl.pages.golf
  (:use hiccup.form-helpers)
  (:use [ring.util.response :only [redirect]])
  (:use tl.pages.global))

(def games (ref []))

(defn create-button
  []
  (blurb
   (form-to
    [:post "/golf.html"]
    (label :name "Game Name:")
    (text-field :name)
    (submit-button "Create"))))

(defn skins-games []
  (conj [:div] (map (fn [{name :name type :type}] (blurb [:h1 name] [:p type])) @games)))

(defn add-game [game]
  (dosync (alter games conj game)))
  
(defn clear-games []
  (dosync (ref-set games [])))

(defn golf []
  (page {} {:title "Golf" :body (conj (skins-games) (create-button))}))

(defn golf-post [name]
  (do
    (add-game {:name name :type "skins-half-hdcp" :rounds []})
    (redirect "/golf.html")))
