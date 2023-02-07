import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Random;

public class JuegoDados {

    public static int[] tiraDados(int numDados) {
        ExecutorService executor = Executors.newFixedThreadPool(numDados);
        int[] resultados = new int[numDados];
        Future<Integer>[] futures = new Future[numDados];

        for (int i = 0; i < numDados; i++) {
            int index = i;
            futures[i] = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Random rand = new Random();
                    int resultado = rand.nextInt(6) + 1;
                    return resultado;
                }
            });
        }

        for (int i = 0; i < numDados; i++) {
            try {
                resultados[i] = futures[i].get();
            } catch (Exception e) {
                System.out.println("Error obteniendo el resultado del dado: " + e);
            }
        }

        executor.shutdown();
        return resultados;
    }

    public static void main(String[] args) {
        int numDados = 3;
        int[] resultados = tiraDados(numDados);
        for (int i = 0; i < numDados; i++) {
            System.out.println("Resultado del dado " + (i + 1) + ": " + resultados[i]);
        }
    }
}

