(ns integrador.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [integrador.core :refer [adicionar-status buscar-aluno cadastrar-alunos calcular-media estatisticas-gerais exibir-menu ler-nota ler-opcao relatorio-notas]]))

;; Testes para funções puras (sem efeitos colaterais)

(deftest test-adicionar-status
  (testing "Adicionar status de aprovado para nota >= 7.0"
    (let [aluno {:nome "João" :nota 8.5}
          resultado (adicionar-status aluno)]
      (is (= "Aprovado" (:status resultado)))
      (is (= (:nome aluno) (:nome resultado)))
      (is (= (:nota aluno) (:nota resultado)))))

  (testing "Adicionar status de reprovado para nota < 7.0"
    (let [aluno {:nome "Maria" :nota 6.0}
          resultado (adicionar-status aluno)]
      (is (= "Reprovado" (:status resultado)))
      (is (= (:nome aluno) (:nome resultado)))
      (is (= (:nota aluno) (:nota resultado)))))

  (testing "Nota exatamente 7.0 deve ser aprovado"
    (let [aluno {:nome "Pedro" :nota 7.0}
          resultado (adicionar-status aluno)]
      (is (= "Aprovado" (:status resultado))))))

(deftest test-calcular-media
  (testing "Calcular média de lista vazia"
    (is (= 0.0 (calcular-media []))))

  (testing "Calcular média de um aluno"
    (let [alunos [{:nome "João" :nota 8.5}]]
      (is (= 8.5 (calcular-media alunos)))))

  (testing "Calcular média de múltiplos alunos"
    (let [alunos [{:nome "João" :nota 8.0}
                  {:nome "Maria" :nota 7.0}
                  {:nome "Pedro" :nota 9.0}]]
      (is (= 8.0 (calcular-media alunos)))))

  (testing "Calcular média com números decimais"
    (let [alunos [{:nome "João" :nota 8.5}
                  {:nome "Maria" :nota 7.3}
                  {:nome "Pedro" :nota 9.2}]]
      (is (= 8.333333333333334 (calcular-media alunos))))))

;; Testes para funções com I/O (usando with-in-str para simular entrada)

(deftest test-ler-opcao
  (testing "Ler opção válida"
    (is (= 1 (with-in-str "1" (ler-opcao))))
    (is (= 5 (with-in-str "5" (ler-opcao))))
    (is (= 0 (with-in-str "0" (ler-opcao)))))

  (testing "Ler entrada inválida retorna 0"
    (is (= 0 (with-in-str "abc" (ler-opcao))))
    (is (= 0 (with-in-str "" (ler-opcao))))
    (is (= 0 (with-in-str "12.5" (ler-opcao))))))

(deftest test-ler-nota
  (testing "Ler nota válida"
    (is (= 8.5 (with-in-str "8.5" (ler-nota))))
    (is (= 7.0 (with-in-str "7" (ler-nota))))
    (is (= 10.0 (with-in-str "10" (ler-nota))))
    (is (= 0.0 (with-in-str "0" (ler-nota)))))

  (testing "Ler entrada inválida retorna nil"
    (is (= nil (with-in-str "abc" (ler-nota))))
    (is (= nil (with-in-str "" (ler-nota))))
    (is (= nil (with-in-str "15" (ler-nota))))))

(deftest test-exibir-menu
  (testing "Exibir menu não lança exceção"
    (is (nil? (with-out-str (exibir-menu))))))

;; Testes para funções de relatório (usando with-out-str para capturar saída)

(deftest test-estatisticas-gerais
  (testing "Estatísticas com lista vazia"
    (let [output (with-out-str (estatisticas-gerais []))]
      (is (.contains output "Nenhum aluno cadastrado!"))))

  (testing "Estatísticas com alunos"
    (let [alunos [{:nome "João" :nota 8.0}
                  {:nome "Maria" :nota 6.0}
                  {:nome "Pedro" :nota 9.0}]
          output (with-out-str (estatisticas-gerais alunos))]
      (is (.contains output "=== ESTATÍSTICAS GERAIS ==="))
      (is (.contains output "Total de alunos cadastrados: 3"))
      (is (.contains output "Número de aprovados: 2"))
      (is (.contains output "Número de reprovados: 1"))
      (is (.contains output "Maior nota: 9.0"))
      (is (.contains output "Menor nota: 6.0"))
      (is (.contains output "Média geral da turma: 7.67"))))

  (testing "Estatísticas com todos aprovados"
    (let [alunos [{:nome "João" :nota 8.0}
                  {:nome "Maria" :nota 7.5}]
          output (with-out-str (estatisticas-gerais alunos))]
      (is (.contains output "Número de aprovados: 2"))
      (is (.contains output "Número de reprovados: 0"))))

  (testing "Estatísticas com todos reprovados"
    (let [alunos [{:nome "João" :nota 5.0}
                  {:nome "Maria" :nota 6.0}]
          output (with-out-str (estatisticas-gerais alunos))]
      (is (.contains output "Número de aprovados: 0"))
      (is (.contains output "Número de reprovados: 2")))))

(deftest test-relatorio-notas
  (testing "Relatório com lista vazia"
    (let [output (with-out-str (relatorio-notas []))]
      (is (.contains output "Nenhum aluno cadastrado!"))))

  (testing "Relatório com alunos"
    (let [alunos [{:nome "João" :nota 8.0}
                  {:nome "Maria" :nota 6.0}
                  {:nome "Pedro" :nota 9.0}]
          output (with-out-str (relatorio-notas alunos))]
      (is (.contains output "=== RELATÓRIO DE NOTAS ==="))
      (is (.contains output "Todos os alunos:"))
      (is (.contains output "Nome: João | Nota: 8.0 | Status: Aprovado"))
      (is (.contains output "Nome: Maria | Nota: 6.0 | Status: Reprovado"))
      (is (.contains output "Nome: Pedro | Nota: 9.0 | Status: Aprovado"))
      (is (.contains output "Alunos aprovados:"))
      (is (.contains output "Nome: João | Nota: 8.0"))
      (is (.contains output "Nome: Pedro | Nota: 9.0"))
      (is (.contains output "Média geral da turma: 7.67"))))

  (testing "Relatório sem alunos aprovados"
    (let [alunos [{:nome "João" :nota 5.0}
                  {:nome "Maria" :nota 6.0}]
          output (with-out-str (relatorio-notas alunos))]
      (is (.contains output "Nenhum aluno aprovado."))))

  (testing "Relatório com um aluno aprovado"
    (let [alunos [{:nome "João" :nota 8.0}]
          output (with-out-str (relatorio-notas alunos))]
      (is (.contains output "Nome: João | Nota: 8.0 | Status: Aprovado"))
      (is (.contains output "Nome: João | Nota: 8.0")))))

;; Testes para buscar-aluno

(deftest test-buscar-aluno
  (testing "Buscar aluno com lista vazia"
    (let [output (with-out-str (buscar-aluno []))]
      (is (.contains output "Nenhum aluno cadastrado!"))))

  (testing "Buscar aluno existente (case insensitive)"
    (let [alunos [{:nome "João Silva" :nota 8.0}
                  {:nome "Maria Santos" :nota 7.5}
                  {:nome "Pedro Costa" :nota 6.0}]
          output (with-in-str "joão silva"
                   (with-out-str (buscar-aluno alunos)))]
      (is (.contains output "Aluno encontrado:"))
      (is (.contains output "Nome: João Silva"))
      (is (.contains output "Nota: 8.0"))
      (is (.contains output "Status: Aprovado"))))

  (testing "Buscar aluno inexistente"
    (let [alunos [{:nome "João Silva" :nota 8.0}
                  {:nome "Maria Santos" :nota 7.5}]
          output (with-in-str "Pedro Costa"
                   (with-out-str (buscar-aluno alunos)))]
      (is (.contains output "Aluno 'Pedro Costa' não encontrado!"))))

  (testing "Buscar aluno com nome parcial (deve encontrar)"
    (let [alunos [{:nome "João Silva" :nota 8.0}
                  {:nome "João Santos" :nota 7.5}]
          output (with-in-str "João"
                   (with-out-str (buscar-aluno alunos)))]
      (is (.contains output "Aluno encontrado:"))
      (is (.contains output "Nome: João Silva"))))

  (testing "Buscar aluno reprovado"
    (let [alunos [{:nome "João Silva" :nota 8.0}
                  {:nome "Maria Santos" :nota 5.5}]
          output (with-in-str "maria santos"
                   (with-out-str (buscar-aluno alunos)))]
      (is (.contains output "Status: Reprovado")))))

;; Testes para cadastrar-alunos (testando apenas casos específicos)

(deftest test-cadastrar-alunos
  (testing "Cadastro finalizado com nome vazio"
    (let [alunos-existentes [{:nome "João" :nota 8.0}]
          result (with-in-str "\n"
                   (cadastrar-alunos alunos-existentes))]
      (is (= alunos-existentes result))))

  (testing "Cadastro de aluno válido"
    (let [alunos-existentes []
          result (with-in-str "João Silva\n8.5\n\n"
                   (cadastrar-alunos alunos-existentes))]
      (is (= 1 (count result)))
      (is (= "João Silva" (:nome (first result))))
      (is (= 8.5 (:nota (first result))))))

  (testing "Cadastro com nota inválida (menor que 0)"
    (let [alunos-existentes []
          result (with-in-str "João\n-1\n8.0\n\n"
                   (cadastrar-alunos alunos-existentes))]
      (is (= 1 (count result)))
      (is (= "João" (:nome (first result))))
      (is (= 8.0 (:nota (first result))))))

  (testing "Cadastro com nota inválida (maior que 10)"
    (let [alunos-existentes []
          result (with-in-str "Maria\n15\n7.5\n\n"
                   (cadastrar-alunos alunos-existentes))]
      (is (= 1 (count result)))
      (is (= "Maria" (:nome (first result))))
      (is (= 7.5 (:nota (first result))))))

  (testing "Cadastro múltiplo de alunos"
    (let [alunos-existentes []
          result (with-in-str "João\n8.0\nMaria\n7.5\nPedro\n9.0\n\n"
                   (cadastrar-alunos alunos-existentes))]
      (is (= 3 (count result)))
      (is (= ["João" "Maria" "Pedro"] (map :nome result)))
      (is (= [8.0 7.5 9.0] (map :nota result))))))

;; Função para executar todos os testes
(defn run-all-tests []
  (println "=== EXECUTANDO TESTES TDD ===")
  (println "Sistema de Gerenciamento de Alunos")
  (println)

  (let [results (clojure.test/run-tests 'integrador.core-test)]
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
        (println "⚠️  Verifique o código e corrija os problemas.")))

    results))

;; Função principal para executar quando chamado como main
(defn -main [& args]
  (run-all-tests))