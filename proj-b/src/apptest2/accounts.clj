(ns apptest2.accounts
  (:gen-class))
;Illustrates record  - records can be used just like hashmaps.
(defrecord Account [name balance])

;creating a record instance.
(def account (->Account "Raju" 1000))

(defn f[]
	(println "hello f from account")
) 