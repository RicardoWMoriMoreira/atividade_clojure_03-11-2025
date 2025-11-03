(ns simple-test
  (:require [clojure.test :refer [deftest is run-tests testing]]))

;; Teste simples para verificar se o ambiente está funcionando
(deftest test-simple
  (testing "Teste básico"
    (is (= 4 (+ 2 2)))
    (is (= "hello" "hello"))))

;; Executar o teste
(run-tests)
