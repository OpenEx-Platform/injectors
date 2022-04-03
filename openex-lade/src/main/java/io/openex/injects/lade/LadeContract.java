package io.openex.injects.lade;

import io.openex.contract.Contractor;
import io.openex.contract.Contract;
import io.openex.injects.lade.config.LadeConfig;
import io.openex.injects.lade.service.LadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LadeContract extends Contractor {

    public static final String TYPE = "openex_lade";

    private LadeConfig config;
    private LadeService ladeService;

    @Autowired
    public void setLadeService(LadeService ladeService) {
        this.ladeService = ladeService;
    }

    @Autowired
    public void setConfig(LadeConfig config) {
        this.config = config;
    }

    @Override
    public boolean isExpose() {
        return config.getEnable();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public List<Contract> contracts() throws Exception {
        return ladeService.buildContracts(isExpose());
    }
}
