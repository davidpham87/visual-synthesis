(ns wyssacademy.visual-synthesis.data
  (:require
   [camel-snake-kebab.core :as csk]
   [camel-snake-kebab.extras :as cske]
   [clojure.java.io :as io]
   [clojure.pprint :as pprint]
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
          (let [studies (vec (for [i [1 2 3]
                                   :let [study-k (keyword (str "study-" i))
                                         explanation-k (keyword (str "explanation-study-" i))]
                                   :when (get m study-k)]
                               {:study (get m study-k) :explanation (get m explanation-k) :rank i}))]
            (-> m
                (select-keys [:link-description :link-name :out :effect :in])
                (assoc :studies studies)))))))

(defn parse-studies [ds]
  (->> (cske/transform-keys csk/->kebab-case-keyword (vec (ds/rows ds)))
       (mapv #(reduce-kv (fn [m k v] (if v (assoc m k v) m)) {} %))))

(def data (ds/->dataset "resources/mock_tables/list_interactions.csv"))
(def studies (ds/->dataset "resources/mock_tables/list_studies_utf8.csv"))

(comment
  (spit (io/file "resources/mock_tables/interactions.edn")
        (with-out-str (pprint/pprint (parse-interactions data))))
  (spit (io/file "resources/mock_tables/interactions.json")
        (->transit-str (parse-interactions data)))
  (ds/row-at studies 0)
  (spit (io/file "resources/mock_tables/studies.edn")
        (with-out-str (pprint/pprint (parse-studies studies))))

  )
