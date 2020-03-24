package slotmachine.hardwareInteface;

public interface IBanditInputhandler {
    void resetRotoryEncoderPosition();
    boolean getButtonIsPushed();
    int getRotoryEncoderPosition();
    void setRotoryEncoderPosition(int value);
    boolean getMoneyPuscherIsActive();
    void pushCoins(int count);
    void setLightActive(int number, boolean state);
    boolean getLightState(int number);
}
