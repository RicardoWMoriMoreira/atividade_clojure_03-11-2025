#!/usr/bin/env clj

(ns run-all-tests
  (:require [clojure.test :as t]))

(defn -main [& args]
  (println "=== EXECUTANDO TESTES TDD ===")
  (println "Sistema de Gerenciamento de Alunos")
  (println)

  (try
    (println "Carregando testes...")
    (require 'integrador.core-test)
    (println "✅ Testes carregados com sucesso!")
    (println)

    (println "Executando testes...")
    (let [results (t/run-tests 'integrador.core-test)]
      (println)
      (println "=== RESULTADO DOS TESTES ===")
      (println (str "• Testes executados: " (:test results)))
      (println (str "• Testes que passaram: " (:pass results)))
      (println (str "• Testes que falharam: " (:fail results)))
      (println (str "• Testes com erro: " (:error results)))
      (println)

      (if (and (= 0 (:fail results)) (= 0 (:error results)))
        (do
          (println "🎉 SUCESSO! Todos os testes passaram!")
          (println "✅ O sistema está funcionando corretamente."))
        (do
          (println "❌ Alguns testes falharam ou apresentaram erros.")
          (println "⚠️  Verifique o código e corrija os problemas."))))

    (catch Exception e
      (println "❌ Erro ao executar testes:")
      (println (.getMessage e))
      (println)
      (println "Soluções possíveis:")
      (println "1. Verifique se está no diretório correto do projeto")
      (println "2. Certifique-se de que os arquivos de teste existem")
      (println "3. Verifique se o deps.edn está configurado corretamente"))))
