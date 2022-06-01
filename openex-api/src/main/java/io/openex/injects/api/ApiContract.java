package io.openex.injects.api;

import io.openex.contract.Contract;
import io.openex.contract.ContractCardinality;
import io.openex.contract.Contractor;
import io.openex.contract.fields.ContractElement;
import io.openex.injects.api.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.openex.contract.Contract.executableContract;
import static io.openex.contract.ContractDef.contractBuilder;
import static io.openex.contract.fields.ContractText.textField;
import static io.openex.contract.fields.ContractTextArea.textareaField;
import static io.openex.contract.fields.ContractTuple.tupleField;

@Component
public class ApiContract extends Contractor {
    public static final String API_POST_CONTRACT = "5948c96c-4064-4c0d-b079-51ec33f31b91";
    public static final String API_GET_CONTRACT = "611f223d-0e95-4f5b-ad89-a09ec2be50ae";
    public static final String TYPE = "openex_api";

    private ApiConfig config;

    @Autowired
    public void setConfig(ApiConfig config) {
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
    public List<Contract> contracts() {
        // Post contract
        List<ContractElement> postInstance = contractBuilder()
                .mandatory(textField("uri", "Uri"))
                .optional(tupleField("headers", "Headers", ContractCardinality.Multiple))
                .mandatory(textareaField("body", "Post body"))
                .build();
        Contract postContract = executableContract(TYPE, isExpose(), API_POST_CONTRACT, "Rest POST", postInstance);
        // Get contract
        List<ContractElement> getInstance = contractBuilder()
                .mandatory(textField("uri", "Uri"))
                .optional(tupleField("headers", "Headers", ContractCardinality.One))
                .build();
        Contract getContract = executableContract(TYPE, isExpose(), API_GET_CONTRACT, "Rest GET", getInstance);
        return List.of(postContract, getContract);
    }
}
