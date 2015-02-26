(ns apptest.core
  (:gen-class)
  (:use clojure.tools.trace)
  )


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Test Clojure reloaded!" )
 )

;Illustrates lazy evaluation. 
(def nums (map #(do (println %) (* % %)) '(1 2 3 4 5)))

;In Clojure we use higher order functions for most data processing tasks.
(defn value-square-sum [m]
	(reduce +
		(map  #(* (second %) (second %)) m)
	)
)

;checks if every one in the mark list got at least min-marks
;and at least one person got max-marks
;With functional approach the code is very close to the
;specification.
(defn check-performance [mark-list, min-marks, max-marks]
	(and
		(every? #(<= min-marks (% :marks)) mark-list)
		(some   #(<= max-marks (% :marks)) mark-list)
	)
)

;filters the mark list entries who got above the cut off marks
(defn list-cutoff [mark-list, cutoff]
	(map
		:name
		(filter #(>= (:marks %) cutoff) mark-list)
	)
)

;A global mark list object illustrating use of atom
(def mark-list 
	(atom [ {:marks 95 :name "Santhosh"} 
			{:marks 50 :name "Venu"} {:marks 95 :name "Rajesh"} {:marks 80 :name "Raj"}]
	)
)


(def points1 {"player1" [30 50] "player2" [50, 70]})

(def points2 {"player3" [40 50] "player1" [25, 75]})

(def points3 {"player2" [41 53 65 90] "player5" [25, 75]})

;merges the points of each player from points1 points2 and points3
(def all-points (merge-with concat points1 points2 points3))

;max value using reduce - use builtin max function in real case
(println (reduce #(if (>= %2 %1) %2 %1) '(5 7 1 9 2)))


;Returns the names of students who got first rank.
(defn first-rank [mark-list]
	(let [max-marks 		(apply max (map :marks mark-list))
		  has-max-marks 	#(= max-marks (:marks %))
		 ]
		(map :name (filter has-max-marks mark-list))
	)
)

(def seq-num (atom 0))

;Atom example - auto incrementing sequence number.
(defn get-seq-num[]
  (swap! seq-num inc)
)

(def account-balance (ref 100))
(def expenditure (ref 0))

;Using refs and software transactional memory
;to atomically read-modify-update multiple refs.
(defn spend[m]	
	(dosync
	  (alter account-balance - m)
	  (alter expenditure + m)
  )
)

(defn get-total[]
   (+ @account-balance @expenditure)
)

;Defines a Java interface.
(definterface Hello
	(hello [this])
)

;interface Hello{
;    void hello();
;}

;Defines a protocol
(defprotocol Test
  (hello [this] "Hello")
)

;Extending a type with a protocol.
;Now hello() can be invoked on File objects
(extend-type java.io.File Test
	(hello [this] (println "Hello for java.io.File"))
)

(defprotocol Producer
  (produce [this])
)


(defprotocol Name
  (get-name [this] )
  (set-name! [this v]) 
)

(defprotocol Consumer
  (consume [this val])
)

;Defining a type implementing a protocol
(deftype MyConsumer [buf] Consumer
	(consume [this val] (println "Myconsumer consume" val))
)

;Defining a type implementing two protocols
;and having a mutable data member.
(deftype GoodName [^:volatile-mutable name] Name Consumer 
  (get-name [this] (.name test))
  (set-name! [this v] (set! name v)) 
  (consume [this val] (println "consume from Goodname " val))
)

;Illustrates multimethod - dynamic dispatch based on 
;the first parameter.
(defmulti hello type)
(defmethod hello java.lang.String [m] (println "hello string " m))

(defmethod hello java.lang.Long [m] (println "hello integer " m))

(defmethod hello :default [m] (println "new object type" m))


(defn classify[v]
   v
)

(defmulti hellox classify)
(defmethod hellox "v1" [m] (println "hellox v1 " m))

(defmethod hellox "v2" [m] (println "hellox v2 " m))

(defmethod hellox :default [m] (println "hellox default" m))

;Illustrates macro. With code as data in Clojure macros can 
;be used to manipulate input code as standard datastructures.
(defmacro noargs [name fn-body]
	(list 'def name
		(list
		  	'fn
			[]
  			fn-body		  			
  		)
	)
)

(noargs test (println "test"))
(def test (fn [] (println "test")))