(ns apptest2.core
  (:gen-class)
  ;Illustrates modular development - requiring from another file.
   (:require [apptest2.accounts :as acc] [apptest2.mod.employees :as emp])
  (:import (java.io File FileReader))
  (:use clojure.tools.trace)
 )

 ;invoking functions "require'ed from another namespace.
 (acc/f)
 ;(emp/f)
 ;(do-stuff)
(File. "somefile")
 
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def config (atom {:name "xyz" :db "abc"}))


(defn add-config [config-key config-val]
    (swap! config assoc config-key config-val)
)

(def accounts {
	"ac1" (ref {:name "Rajesh" :balance 5000}) 
	"ac2" (ref {:name "Brijesh" :balance 10000})
})

;(transfer-money "ac2" "ac1" 500)
;Useful for debugging - instead of defn use deftrace.
(deftrace update-balance[account delta]
	(let [new-balance (+ (:balance account) delta)]
		(assoc account :balance new-balance)
	)
)
;(update-balance (accounts "ac1") 50) 
;Illustrates refs and STM 
(defn transfer-money [from to amount]
	(let [from-ac (accounts from) to-ac (accounts to)]
		(dosync 
			(alter from-ac update-balance (- amount))
			(alter to-ac update-balance amount)
		)
   )
)

