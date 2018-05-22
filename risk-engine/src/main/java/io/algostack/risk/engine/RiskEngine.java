package io.algostack.risk.engine;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class RiskEngine {


    private static final Logger logger = Logger.getLogger(RiskEngine.class.getName());

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */

        logger.info("Maket data path: " + AppProperties.getMarketDataPath());
        final AppContext context = AppContext.getInstance();

        int port = AppProperties.getPort();
        server = ServerBuilder.forPort(port)
                .addService(context.getRiskEngineGrpc())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            //   HelloWorldServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        RiskEngine riskEngine = new RiskEngine();
        riskEngine.start();
        riskEngine.blockUntilShutdown();
    }


}
