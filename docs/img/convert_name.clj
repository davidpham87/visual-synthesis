(ns convert-name
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.java.shell :as sh]
   [clojure.string :as str]))

(defn ls-webp []
  (->> #"\n"
       (str/split (:out (sh/sh "ls")))
       (filter #(re-find #"webp$" %))))

(defn ls-png []
  (->> #"\n"
       (str/split (:out (sh/sh "ls")))
       (filter #(re-find #"png$" %))))

(defn convert-names []
  (doseq [[old new] (mapv #(vector % (csk/->kebab-case-string %)) (ls-png))]
    (:out (sh/sh "mv" old new))))

(def images
  [{:src "artisanal-mines.png", :zoom 0.125}
   {:src "biodiversity.png", :zoom 0.12}
   {:src "climate-change.png", :zoom 1.2}
   {:src "clove-agf.png", :zoom 0.12}
   {:src "commercial-agriculture.png", :zoom 0.15}
   {:src "cultural-es.png", :zoom 0.2}
   {:src "fallow-vanilla.png", :zoom 0.1}
   {:src "vanilla-forest.png", :zoom 0.1}
   {:src "forest-fragment.png", :zoom 0.12}
   {:src "infrastructure.png", :zoom 0.2}
   {:src "infrastructure-sm.png", :zoom 0.52}
   {:src "rice-paddy-cut.png", :zoom 0.125}
   {:src "off-farm-income.png", :zoom 0.065}
   {:src "old_growth_forest.png", :zoom 0.12}
   {:src "out-landscape-influences-4.png", :zoom 0.25}
   {:src "pasture.png", :zoom 0.12}
   {:src "protected-forest.png", :zoom 0.12}
   {:src "regulating-es.png", :zoom 0.1}
   {:src "rice-paddy.png", :zoom 0.1}
   {:src "shifting-cultivation.png", :zoom 0.15}
   {:src "subsistence-agriculture.png", :zoom 0.12}
   {:src "unprotected-forest.png", :zoom 0.12}
   {:src "provision-services.png", :zoom 0.12}
   {:src "water-bodies-4-3.png", :zoom 0.13}
   {:src "wellbeing.png", :zoom 0.2}])

(defn resize-images []
  (doseq [{:keys [src zoom]} images]
    (println  [ "convert" "-scale" (str (int (* 100 zoom)) "%") src src])
    (println (:out (sh/sh "convert" "-scale" (str (int (* 100 zoom)) "%")   "-unsharp" "0x1"
                          src src)))))

(comment
  (convert-names)
  (resize-images)
  )
