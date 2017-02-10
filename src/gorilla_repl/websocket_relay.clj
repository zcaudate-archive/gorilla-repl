;;;; This file is part of gorilla-repl. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

;;; A websocket handler that passes messages back and forth to an already running nREPL server.

(ns gorilla-repl.websocket-relay
  (:require [aleph.http.server :as server]
            [aleph.http :as http]
            [manifold.stream :as stream]
            [clojure.tools.nrepl :as nrepl]
            [cheshire.core :as json]))

;; We will open a single connection to the nREPL server for the life of the application. It will be stored here.
(def conn (atom nil))

;; Doing it this way with an atom feels wrong, but I can't figure out how to thread an argument into Compojure's
;; routing macro, so I can't pass the connection around, to give a more functional API.
(defn connect-to-nrepl
  "Connect to the nREPL server and store the connection."
  [port]
  (let [new-conn (nrepl/connect :port port)]
    (swap! conn (fn [x] new-conn))))

(defn ring-handler
  "This ring handler expects the client to make a websocket connection to the endpoint. It relays messages back and
  forth to an nREPL server. A connection to the nREPL server must have previously been made with 'connect-to-nrepl'.
  Messages are mapped back and forth to JSON as they pass through the relay."
  [request]
  (let [ws  @(http/websocket-connection request)
        process (stream/consume
                 (fn [msg]
                   ;;(println "RECIEVED" msg)
                   (let [parsed-message (assoc (json/parse-string msg true) :as-html 1)
                         client  (nrepl/client @conn Long/MAX_VALUE)
                         replies (nrepl/message client parsed-message)]
                     (doseq [reply replies]
                       ;;(println "SENT:" reply)
                       (stream/put! ws (json/generate-string reply))))
                   msg)
                 ws)]
    ws
    {}))


