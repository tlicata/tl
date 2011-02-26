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

