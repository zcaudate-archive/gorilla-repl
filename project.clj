;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(defproject im.chit/gorilla-repl "0.4.5"
  :description "A rich REPL for Clojure in the notebook style."
  :url "https://github.com/JonyEpsilon/gorilla-repl"
  :license {:name "MIT"}
  :dependencies ^:replace [[org.clojure/clojure "1.8.0"]
                           [aleph "0.4.1"]
                           [ring/ring-json "0.4.0"]
                           [cheshire "5.7.0"]
                           [compojure "1.5.2"]
                           [org.slf4j/slf4j-api "1.7.22"]
                           [ch.qos.logback/logback-classic "1.2.1"]
                           [gorilla-renderable "2.0.0"]
                           [gorilla-plot "0.1.4"]
                           [javax.servlet/servlet-api "2.5"]
                           [grimradical/clj-semver "0.3.0"]
                           [cider/cider-nrepl "0.14.0"]
                           [org.clojure/tools.nrepl "0.2.12"]]
  :main ^:skip-aot gorilla-repl.core
  :target-path "target/%s")
