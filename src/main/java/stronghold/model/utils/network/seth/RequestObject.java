package stronghold.model.utils.network.seth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class RequestObject implements Serializable {
    private String serialCode;
    private Object[] args;

    public RequestObject(String serialCode, Object... args) {
        this.serialCode = serialCode;
        this.args = args;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object... args) {
        this.args = args;
    }
}
