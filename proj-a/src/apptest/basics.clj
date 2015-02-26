;explicit tail recursion using recur
(defn 	fact[n r] 
		(if (= n 0) 
			r 
			(recur (dec n) (* n r))
		)
)

;linear recursion
(defn 	lfact[n] 
		(if (= n 0) 
			1 
			(* n (lfact (dec n)))
		)
)
;tail recursion using loop
(println 
	(loop [n 100 r 1N] 
		(if 	(= n 0) 
			r 
			(recur (dec n) (* n r))
		)
	)
)

;Java interop
(defn upper[x] (.toUpperCase x))

(defn trim [y] (.trim y))

(defn f
   ([] (println "no args"))
   ([n] (println "arg " n))
   ([m & args] (println "varags " args))
)

(defn fx [& args]
	(println args)
)

(def expr [x y z] (+ (* x y) z))

