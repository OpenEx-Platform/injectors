package io.openex.injects.http;

import io.openex.contract.Contract;
import io.openex.contract.ContractCardinality;
import io.openex.contract.ContractConfig;
import io.openex.contract.Contractor;
import io.openex.contract.fields.ContractElement;
import io.openex.helper.SupportedLanguage;
import io.openex.injects.http.config.HttpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static io.openex.contract.Contract.executableContract;
import static io.openex.contract.ContractDef.contractBuilder;
import static io.openex.contract.fields.ContractText.textField;
import static io.openex.contract.fields.ContractTextArea.textareaField;
import static io.openex.contract.fields.ContractTuple.tupleField;
import static io.openex.helper.SupportedLanguage.en;
import static io.openex.helper.SupportedLanguage.fr;

@Component
public class HttpContract extends Contractor {
    public static final String TYPE = "openex_http";
    public static final String HTTP_POST_CONTRACT = "5948c96c-4064-4c0d-b079-51ec33f31b91";
    public static final String HTTP_GET_CONTRACT = "611f223d-0e95-4f5b-ad89-a09ec2be50ae";

    private HttpConfig config;

    @Autowired
    public void setConfig(HttpConfig config) {
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
    public ContractConfig getConfig() {
        Map<SupportedLanguage, String> labels = Map.of(en, "HTTP Request", fr, "Requête HTTP");
        return new ContractConfig(TYPE, labels, "#00bcd4", "/img/http.png", isExpose());
    }

    @Override
    public List<Contract> contracts() {
        ContractConfig contractConfig = getConfig();
        // Post contract
        List<ContractElement> postInstance = contractBuilder().mandatory(textField("uri", "Uri")).optional(tupleField("headers", "Headers", ContractCardinality.Multiple)).mandatory(textareaField("body", "Post body")).build();
        Contract postContract = executableContract(contractConfig, HTTP_POST_CONTRACT, Map.of(en, "HTTP Request - POST", fr, "Requête HTTP - POST"), postInstance);
        // Get contract
        List<ContractElement> getInstance = contractBuilder().mandatory(textField("uri", "Uri")).optional(tupleField("headers", "Headers", ContractCardinality.Multiple)).build();
        Contract getContract = executableContract(contractConfig, HTTP_GET_CONTRACT, Map.of(en, "HTTP Request - GET", fr, "Requête HTTP - GET"), getInstance);
        return List.of(postContract, getContract);
    }
}
