(ns teste-tdd
  (:require [clojure.test :refer [deftest is run-tests testing]]))

;; Teste simples
(deftest teste-basico
  (testing "2 + 2 deve ser 4"
    (is (= 4 (+ 2 2))))
  (testing "String igual"
    (is (= "hello" "hello"))))

;; Executar testes
(println "=== EXECUTANDO TESTES TDD ===")
(run-tests)
