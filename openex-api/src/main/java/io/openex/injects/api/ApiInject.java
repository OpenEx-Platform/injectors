package io.openex.injects.api;

import io.openex.contract.Contract;
import io.openex.database.model.Execution;
import io.openex.execution.ExecutableInject;
import io.openex.execution.Injector;
import io.openex.injects.api.model.ApiRestGet;
import io.openex.injects.api.model.ApiRestPost;
import io.openex.injects.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.openex.database.model.ExecutionTrace.traceError;
import static io.openex.database.model.ExecutionTrace.traceSuccess;
import static io.openex.injects.api.ApiContract.API_GET_CONTRACT;
import static io.openex.injects.api.ApiContract.API_POST_CONTRACT;

@Component(ApiContract.TYPE)
public class ApiInject extends Injector {

    private ApiService apiService;

    @Autowired
    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    private String processExecution(ExecutableInject injection, Contract contract) throws Exception {
        return switch (contract.getId()) {
            case API_POST_CONTRACT -> apiService.executeRestPost(contentConvert(injection, ApiRestPost.class));
            case API_GET_CONTRACT -> apiService.executeRestGet(contentConvert(injection, ApiRestGet.class));
            default -> throw new UnsupportedOperationException("Unknown contract " + contract.getId());
        };
    }

    @Override
    public void process(Execution execution, ExecutableInject injection, Contract contract) {
        try {
            String callResult = processExecution(injection, contract);
            String message = "Api request sent (" + callResult + ")";
            execution.addTrace(traceSuccess("api", message));
        } catch (Exception e) {
            execution.addTrace(traceError("api", e.getMessage(), e));
        }
    }
}
