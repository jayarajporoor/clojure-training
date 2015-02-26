(ns apptest2.mod.employees
  (:gen-class))
  
(defn do-stuff[]
   (println "mod employees do stuff")
)

(defn ^:private f[]
	(println "hello f from employee")
) 