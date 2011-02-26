(ns tl.pages.home
  (:use [clojure.contrib.str-utils :only [str-join]]
		[hiccup.page-helpers :only [link-to]])
  (:require [appengine-magic.services.user :as us]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
										  "Programmer"
										  "( Golfer | Pingponger )"
										  "( Lockport, NY | Washingon, DC )"])])

(defn home-page []
  {:title ["Home"]
   :body [welcome-blurb]})

(defn contact-page []
  {:title ["Contact"]
   :body [[:div [:p "You can write to me at tim at this domain name."]]]})

(defn admin-page []
  {:title ["Admin"]
   :body [[:div
		   [:p "You're an admin baby"]
		   (link-to (us/logout-url) "Log out")]]})

(defn login-page []
  {:title ["Login"]
   :body [[:div [:p (if (us/user-logged-in?)
					  (link-to (us/logout-url) "Log out")
					  (link-to (us/login-url) "Log in"))]]]})

(def videos [{:title "Geico - Hurt you" :id "mwWfU18boOI"}
			 {:title "Sounds - Hurt you" :id "Krh5s8p48J4"}
			 {:title "Tragically Hip - New Orleans is Sinking (w/ Ahead by a Century)" :id "AZwm_OKh6bw"}
			 {:title "Jethro Tull - Locomotive Breath" :id "gdz_G1VGJ4c"}
			 {:title "Tragically Hip - Fully Completely" :id "pEGyKECUh80"}
			 {:title "Ratatat - Mi Viejo + Mirando [Live]" :id "oM3MdixTdfA"}
			 {:title "Handsome Jack - 5-1-08 Camera 1 (and only)" :id "4tc_GxVtcPE"}])

(defn youtube-list []
  (let [link "http://www.youtube.com/watch?v="
		embed "http://www.youtube.com/v/"]
	(vec (conj (map (fn [v]
					  [:li (link-to (str link (:id v)) (:title v))])
					videos)
			   :ul))))

(defn youtubes []
  {:title ["Hello Youtubes"]
   :body [(youtube-list)]})

		
