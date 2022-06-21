(ns convert-name
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.java.shell :as sh]
   [clojure.string :as str]))

(defn ls-webp []
  (->> #"\n"
       (str/split (:out (sh/sh "ls")))
       (filter #(re-find #"webp$" %))))

(doseq [[old new] (mapv #(vector % (csk/->kebab-case-string %)) (ls-webp))]
  (:out (sh/sh "mv" old new)))
