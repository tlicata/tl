(ns tl.pages.home
  (:use [appengine-magic.services.user :only [login-url logout-url user-logged-in?]]
		[clojure.contrib.str-utils :only [str-join]]
		[hiccup.page-helpers :only [link-to]]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
										  "Programmer"
										  "( Golfer | Pingponger )"
										  "( Lockport, NY | Washingon, DC )"])])

(defn home-page []
  {:title ["Home"]
   :body [welcome-blurb]
   :css ["yeah.css"]
   :js ["some.js"]})

(defn contact-page []
  {:title ["Contact"]
   :body [[:div [:p "You can write to me at tim at this domain name."]]]})

(defn admin-page []
  {:title ["Admin"]
   :body [[:div
		   [:p "You're an admin baby"]
		   (link-to (logout-url) "Log out")]]})

(defn login-page []
  {:title ["Login"]
   :body [[:div [:p (if (user-logged-in?)
					  (link-to (logout-url) "Log out")
					  (link-to (login-url) "Log in"))]]]})
