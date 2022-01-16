(ns wyssacademy.visual-synthesis.db
  (:require
   [datascript.core :as d]))

(def schema
  #:interaction{:id {:db/unique :db.unique/identity}
                :in {:db/index true}
                :out {:db/index true}})

(def empty-ds (d/create-conn schema))

(def default-db
  {:project "visual-synthesis"
   :ds {:interaction empty-ds}
   :user-input {}
   :ui-states {}})


(comment

  )
