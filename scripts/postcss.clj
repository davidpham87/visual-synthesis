(ns postcss
  (:require
   [babashka.tasks :as tasks]
   [clojure.string :as str]))

(defn watch!
  [src dst]
  (tasks/shell {:extra-env {"TAILWIND_MODE" "watch"}}
               (str/join " " ["./node_modules/.bin/postcss" src "-o" dst "--verbose" "-w"])))

(defn release!
  [src dst]
  (tasks/shell {:extra-env {"NODE_MODE" "production"}}
               (str/join " " ["./node_modules/.bin/postcss" src "-o" dst "--verbose"])))
