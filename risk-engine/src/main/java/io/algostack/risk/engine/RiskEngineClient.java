package io.algostack.risk.engine;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import io.algostack.risk.riskengine.CalcRequestProto;
import io.algostack.risk.riskengine.CalcResponseProto;
import io.algostack.risk.riskengine.RiskEngineGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class RiskEngineClient {
    private static final Logger logger = Logger.getLogger(RiskEngineClient.class.getName());

    private final ManagedChannel channel;
    private final RiskEngineGrpc.RiskEngineBlockingStub blockingStub;


    public RiskEngineClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }


    RiskEngineClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = RiskEngineGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Say hello to server. */
    public String calcES(String portfolio) {
        CalcRequestProto request = CalcRequestProto.newBuilder().setName(portfolio).build();
        CalcResponseProto response;
        try {
            response = blockingStub.calcVar(request);
            System.out.println("Response  for portfolio: "  + portfolio + "--" + response.getMessage());
            return response.getMessage();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return "";
        }

    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        final RiskEngineClient client = new RiskEngineClient("localhost", 50051);
        try {
            final Random random = new Random();
            final List<String> portfolios = IntStream.rangeClosed(1, 10).mapToObj(i -> "PORTFOLIO" + i).collect(Collectors.toList());
            /* Access a service running on the local machine on port 50051 */
            for (int i=0; i<10; i++) {
                List<String> itdPortfolios = ImmutableList.of(portfolios.get(random.nextInt(portfolios.size())), portfolios.get(random.nextInt(portfolios.size())));
                Stopwatch stopwatch = Stopwatch.createStarted();
                itdPortfolios
                        .parallelStream()
                        .map(client::calcES)
                        .collect(Collectors.toList());
                System.out.println("Calculated ES for: " + itdPortfolios + "in: " + stopwatch);

                itdPortfolios
                        .parallelStream()
                        .map(client::calcES)
                        .collect(Collectors.toList());
                System.out.println("Calculated ES for second time: " + itdPortfolios + "in: " + stopwatch);

                Thread.sleep(2000);
            }
        } finally {
            client.shutdown();
        }
    }
}