package io.algostack.risk.engine;

import io.algostack.risk.riskengine.CalcRequestProto;
import io.algostack.risk.riskengine.CalcResponseProto;
import io.algostack.risk.riskengine.RiskEngineGrpc;
import io.grpc.stub.StreamObserver;

public class RiskEngineGrpcImpl extends RiskEngineGrpc.RiskEngineImplBase {

    private final ExpectedShortfallService expectedShortfallService;

    public RiskEngineGrpcImpl(ExpectedShortfallService expectedShortfallService) {
        this.expectedShortfallService = expectedShortfallService;
    }

    @Override
    public void calcVar(CalcRequestProto request, StreamObserver<CalcResponseProto> responseObserver) {
        final String calcES = expectedShortfallService.calcES(request.getName());
        final CalcResponseProto responseProto = CalcResponseProto.newBuilder().setMessage(calcES).build();
        responseObserver.onNext(responseProto);
        responseObserver.onCompleted();
    }
}