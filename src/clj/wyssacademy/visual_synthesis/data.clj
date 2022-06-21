(ns wyssacademy.visual-synthesis.data
  (:require
   [camel-snake-kebab.core :as csk]
   [camel-snake-kebab.extras :as cske]
   [clojure.java.io :as io]
   [clojure.pprint :as pprint]
   [clojure.set :as set]
   [clojure.string :as str]
   [cognitect.transit :as transit]
   [tech.v3.dataset :as ds])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream]))

(defn ->transit-str [data]
  (let [out (ByteArrayOutputStream. 4096)
        writer (transit/writer out :json)]
    (transit/write writer data)
    (.toString out)))

(defn parse-interactions [ds]
  (->> (cske/transform-keys csk/->kebab-case-keyword (vec (ds/rows ds)))
       (mapv
        (fn [m]
          (let [studies (vec (for [i (range 1 11)
                                   :let [study-k (keyword (str "study-" i))
                                         explanation-k (keyword (str "finding-study-" i))]
                                   :when (get m study-k)]
                               {:study (get m study-k) :explanation (get m explanation-k) :rank i}))]
            (-> m
                (select-keys [:link-summary :link-name :from :effect :to :agreement-between-studies])
                (set/rename-keys {:from :out :to :in :link-summary :link-description})
                (assoc :studies studies)))))
       (filterv :effect)))

(defn parse-studies [ds]
  (->> (cske/transform-keys csk/->kebab-case-keyword (vec (ds/rows ds)))
       (mapv #(reduce-kv (fn [m k v] (if v (assoc m k v) m)) {} %))
       (mapv #(update % :abstract str))))

#_(def data (ds/->dataset "resources/mock_tables/list_interactions.csv"))
#_(def studies (ds/->dataset "resources/mock_tables/list_studies_utf8.csv"))
(def data (ds/->dataset "resources/interaction_list_systematic.csv"))
#_(def studies (ds/->dataset "resources/final_articles.csv"))

(comment
  (spit (io/file "resources/interactions.edn")
        (with-out-str (pprint/pprint (parse-interactions data))))

  (->> data
       ds/rows
       vec
       (drop 10)
       (take 10)
       (cske/transform-keys csk/->kebab-case-keyword)
       vec
       (mapv #(-> (sort-by first %) vec)))

  (spit (io/file "resources/interactions.json")
        (->transit-str (parse-interactions data)))
  (ds/row-at  0)
  (first (ds/rows studies))

  (spit (io/file "resources/studies.edn")
        (with-out-str (pprint/pprint (parse-studies studies))))

  )
