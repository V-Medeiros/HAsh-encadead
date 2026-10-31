import java.util.Random;

public class Main {
    // Cada no guarda um valor e a referencia para o proximo no.
    static class No {
        int valor;
        No proximo;

        No(int valor) {
            this.valor = valor;
        }
    }

    public static void main(String[] args) {
        final int POSICOES = 100;
        final int ELEMENTOS = 1000;

        No[] matriz = new No[POSICOES];
        int[] ultimosTres = new int[3];
        int quantidadeUltimos = 0;
        int colisoes = 0;
        Random random = new Random(1090);

        for (int i = 0; i < ELEMENTOS; i++) {
            // nextInt exclui o limite superior: 100001 inclui o valor 100000.
            int valor = random.nextInt(100001);
            int posicao = valor % POSICOES;
            No novo = new No(valor);

            if (matriz[posicao] == null) {
                matriz[posicao] = novo;
            } else {
                // Percorre os nos manualmente e insere no fim da lista.
                No atual = matriz[posicao];
                while (atual.proximo != null) {
                    atual = atual.proximo;
                }
                atual.proximo = novo;
                colisoes++;

                // Guarda apenas insercoes que ocorreram por colisao.
                if (quantidadeUltimos < 3) {
                    ultimosTres[quantidadeUltimos] = valor;
                    quantidadeUltimos++;
                } else {
                    ultimosTres[0] = ultimosTres[1];
                    ultimosTres[1] = ultimosTres[2];
                    ultimosTres[2] = valor;
                }
            }
        }

        int listasEncadeadas = 0;
        int posicoesVazias = 0;
        int posicoesOcupadas = 0;
        int totalArmazenado = 0;
        int maiorTamanho = 0;
        int posicaoMaior = -1;

        for (int i = 0; i < POSICOES; i++) {
            if (matriz[i] == null) {
                posicoesVazias++;
            } else {
                posicoesOcupadas++;
                int tamanho = 0;
                No atual = matriz[i];
                while (atual != null) {
                    tamanho++;
                    atual = atual.proximo;
                }
                totalArmazenado += tamanho;

                // Uma lista gerada por colisao possui pelo menos dois nos.
                if (tamanho > 1) {
                    listasEncadeadas++;
                }
                // Em caso de empate, permanece a posicao de menor indice.
                if (tamanho > maiorTamanho) {
                    maiorTamanho = tamanho;
                    posicaoMaior = i;
                }
            }
        }

        System.out.println("Semente: 1090");
        System.out.println("Intervalo: 0 a 100000 (inclusive)");
        System.out.println("Elementos armazenados: " + totalArmazenado);
        System.out.println("Posicoes da matriz: " + POSICOES);
        System.out.println("Listas encadeadas geradas por colisao: " + listasEncadeadas);
        System.out.println("Posicoes ocupadas: " + posicoesOcupadas);
        System.out.println("Posicoes vazias: " + posicoesVazias);
        System.out.println("Total de colisoes: " + colisoes);
        System.out.println("Maior lista: posicao " + posicaoMaior
                + ", com " + maiorTamanho + " elementos");

        if (posicaoMaior >= 0) {
            No atual = matriz[posicaoMaior];
            while (atual != null) {
                System.out.print(atual.valor);
                if (atual.proximo != null) {
                    System.out.print(" -> ");
                }
                atual = atual.proximo;
            }
            System.out.println();
        }

        System.out.print("Tres ultimos valores inseridos por colisao: ");
        for (int i = 0; i < quantidadeUltimos; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(ultimosTres[i]);
        }
        System.out.println();
    }
}
