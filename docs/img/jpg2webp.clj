(ns jpg2webp
  (:require
   [clojure.java.shell :as sh]
   [clojure.string :as str]))

(defn ls-webp []
  (->> #"\n"
       (str/split (:out (sh/sh "ls")))
       (filter #(re-find #"webp$" %))))

(defn ls-jpg []
  (->> #"\n"
       (str/split (:out (sh/sh "ls")))
       (filter #(re-find #"jpg$" %))))

(defn ls-png []
  (->> #"\n"
       (str/split (:out (sh/sh "ls")))
       (filter #(re-find #"png$" %))))

(doseq [s (ls-jpg)]
  (let [out (str/replace s #"\.jpg$" ".webp")]
    (println (sh/sh "cwebp" s "-o" out))))

(doseq [s (ls-png)]
  (let [out (str/replace s #"\.png$" ".webp")]
    (println (sh/sh "cwebp" s "-o" out))))

(doseq [s (ls-webp)]
  (let [out (str/replace s #"\.webp$" ".png")]
    (println (sh/sh "dwebp" s "-o" out))))
