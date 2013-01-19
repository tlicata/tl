(ns tl.user
  (:require [noir.session :as session]
            [noir.util.crypt :as crypt]
            [noir.validation :as vali]
            [tl.db :as db]))

;; db
(defn all []
  (db/get-all-users))
(defn add [user pass]
  (db/add-user user pass))
(defn delete [user]
  (db/delete-user user))
(defn stored-pass [user]
  (db/get-stored-pass user))

;; session
(defn admin? []
  (session/get :admin))

(defn current-user []
  (session/get :username))

(defn login! [{:keys [username password] :as user}]
  (let [stored-pass (stored-pass username)]
    (if (and stored-pass (crypt/compare password stored-pass))
      (do
        (session/put! :admin true)
        (session/put! :username username))
      (vali/set-error :username "Invalid username or password"))))

(defn logout! []
  (do
    (session/remove! :admin)
    (session/remove! :username)))
