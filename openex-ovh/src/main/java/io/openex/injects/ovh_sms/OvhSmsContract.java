package io.openex.injects.ovh_sms;

import io.openex.contract.Contract;
import io.openex.contract.Contractor;
import io.openex.contract.fields.ContractElement;
import io.openex.injects.ovh_sms.config.OvhSmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.openex.contract.Contract.executableContract;
import static io.openex.contract.ContractCardinality.Multiple;
import static io.openex.contract.ContractDef.contractBuilder;
import static io.openex.contract.fields.ContractAudience.audienceField;
import static io.openex.contract.fields.ContractTextArea.textareaField;

@Component
public class OvhSmsContract extends Contractor {

    public static final String OVH_DEFAULT = "e9e902bc-b03d-4223-89e1-fca093ac79dd";
    public static final String TYPE = "openex_ovh_sms";

    private OvhSmsConfig config;

    @Autowired
    public void setConfig(OvhSmsConfig config) {
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
        List<ContractElement> instance = contractBuilder()
                .mandatory(audienceField("audiences", "Audiences", Multiple))
                .mandatory(textareaField("message", "Message"))
                .build();
        return List.of(executableContract(TYPE, isExpose(), OVH_DEFAULT, "Send a SMS", instance));
    }
}
