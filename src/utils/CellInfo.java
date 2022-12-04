package utils;

import java.io.Serializable;

public class CellInfo implements Serializable {
    private byte strength;
    private Boolean isHuman;

    public CellInfo(byte strength, Boolean isHuman){
        this.strength = strength;
        this.isHuman = isHuman;
    }

    public byte getStrength() {
        return strength;
    }
    public boolean isHuman(){
        return isHuman;
    }
}
