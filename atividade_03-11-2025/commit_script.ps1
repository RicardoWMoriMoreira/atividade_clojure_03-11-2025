# Script para fazer commits do projeto Clojure
Set-Location "C:\Users\ri_wa\OneDrive\Área de Trabalho\RICARDO\PARADIGMA_DA_PROGRAMAÇÃO\LISTAS_DE_EXERCICIOS\2º_BIM\atividade_03-11-2025"

# Commit 1: Configuração inicial
git add .gitignore README.md project.clj deps.edn
git commit -m "feat: configuração inicial do projeto - project.clj, deps.edn, README.md, .gitignore"

# Commit 2: Sistema principal
git add src/
git commit -m "feat: implementação do sistema principal de gerenciamento de alunos

- Menu interativo com 5 opções
- Cadastro de alunos com validação de notas
- Relatório de notas com status de aprovação
- Busca de alunos por nome (case insensitive)
- Estatísticas gerais da turma
- Cálculo de médias e percentuais"

# Commit 3: Testes TDD
git add test/
git commit -m "test: implementação completa de testes TDD

- Testes para funções puras (adicionar-status, calcular-media)
- Testes para funções com I/O (ler-opcao, ler-nota, exibir-menu)
- Testes para relatórios (estatisticas-gerais, relatorio-notas)
- Testes para funcionalidades (buscar-aluno, cadastrar-alunos)
- Cobertura completa com cenários de edge cases
- Estratégias de isolamento usando with-in-str e with-out-str"

# Commit 4: Scripts auxiliares
git add run_tests.clj executar_testes.clj run_all_tests.clj simple_test.clj test_simple.clj teste_tdd.clj
git commit -m "feat: scripts auxiliares para execução de testes

- Scripts para execução automatizada dos testes
- Funções utilitárias para desenvolvimento TDD
- Scripts de demonstração e validação"

# Push para GitHub
git push -u origin master
