(ns tl.pages.home
  (:use [appengine-magic.services.user :only [logout-url]]
		[clojure.contrib.str-utils :only [str-join]]
		[hiccup.page-helpers :only [link-to]]
		[tl.pages.global :only [page]]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
										  "Programmer"
										  "( Golfer | Pingponger )"
										  "( Lockport, NY | Washingon, DC )"])])

(defn home-page [request]
  (page {:title "Home"
		 :html [welcome-blurb]
		 :request request}))

(defn contact-page [request]
  (page {:title "Contact"
		 :html [[:div [:p "You can write to me at tim at this domain name."]]]
		 :request request}))

(defn admin-page [request]
  (page {:title "Admin"
		 :html [[:div
				 [:p "You're an admin baby"]
				 (link-to (logout-url) "Log out")]]}))
