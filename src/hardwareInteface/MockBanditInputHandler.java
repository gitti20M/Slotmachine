package hardwareInteface;
import slotmachine.hardwareInteface.IBanditInputhandler;

public class MockBanditInputHandler implements IBanditInputhandler {
  protected boolean mButtonState;
  protected int mRotoryEncoderState;
  protected int mCoinsToDrop;
  protected boolean[] mLight;
  
  public MockBanditInputHandler() {
    mButtonState = false;
    mRotoryEncoderState = 0;
    mLight = new boolean[5];
    for(int i = 0; i < mLight.length; i++){  
        mLight[i] = false;
    }
  }
  
  @Override
  public void resetRotoryEncoderPosition() {
    mRotoryEncoderState = 0;
  }

  @Override
  public boolean getButtonIsPushed() {
    return mButtonState;
  }

  @Override
  public int getRotoryEncoderPosition() {
    return mRotoryEncoderState;
  }
  
  @Override
  public void setRotoryEncoderPosition(int value) {
    mRotoryEncoderState = value;
  }
  
  private void setButtonState(boolean value){
    mButtonState = value;
  }
  
  private void setRotoryEncoderState(int value){
    mRotoryEncoderState = value;
  }
  
  public void pushButten(long time){
     setButtonState(true);
    Thread thread = new Thread(new PushButtonRunnable(this, time));
    thread.start();
  }
  
  public void moveEncoder(long time, int count){
    Thread thread = new Thread(new MoveRotoryEncoderRunnable(this, time, count));
    thread.start();
  }

  @Override
  public String toString() {
    return "ButtonState = " + mButtonState + " RotoryEncoderState = " + mRotoryEncoderState;
  }

  @Override
  public boolean getMoneyPuscherIsActive() {
    return mCoinsToDrop <= 0;
  }

  @Override
  public void pushCoins(int count) {
    mCoinsToDrop = count;
    Thread thread = new Thread(new dropCoinsRunnable(this));
    thread.start();
  }

  @Override
  public void setLightActive(int number, boolean state) {
     if(number < mLight.length) {
           mLight[number] = state;
     }
  }

  @Override
  public boolean getLightState(int number) {
    if(number < mLight.length) {
        return mLight[number];
    }
    throw new IndexOutOfBoundsException();
  }
  static class MoveRotoryEncoderRunnable implements Runnable{
    MockBanditInputHandler mMock;
    int mCount;
    int mCurrent;
    int mDir;
    long mStep;
    public MoveRotoryEncoderRunnable(MockBanditInputHandler mbih, long time, int count) {
      mMock = mbih;
      mCount = count;
      mCurrent = 0;
      mDir = count / Math.abs(count);
      mStep = time / Math.abs(count);
    }
    
    @Override
    public void run() {
      while(mCurrent < mCount * mDir) {
        try {
          Thread.sleep(mStep);
        } catch (InterruptedException ignored) {}
        mMock.setRotoryEncoderState(mMock.getRotoryEncoderPosition() + mDir);
        mCurrent++;
      }
    }
  }
  static class PushButtonRunnable implements Runnable{
    MockBanditInputHandler mMock;
    long mStep;
    public PushButtonRunnable(MockBanditInputHandler mbih, long time) {
      mMock = mbih;
      mStep = time;
    }
    
    @Override
    public void run() {
      mMock.setButtonState(true);
        try {
          Thread.sleep(mStep);
        } catch (InterruptedException ignored) {}
        mMock.setButtonState(false);
    }
  }
  static class dropCoinsRunnable implements Runnable{
    MockBanditInputHandler mMock;
    long mStep = 1000;
    public dropCoinsRunnable(MockBanditInputHandler mbih) {
      mMock = mbih;
    }
    
    @Override
    public void run() {
     while(mMock.mCoinsToDrop > 0) {
        try {
          Thread.sleep(mStep);
        } catch (InterruptedException ex) {
          
        }
        mMock.mCoinsToDrop--;
      }
    }
  }
}
