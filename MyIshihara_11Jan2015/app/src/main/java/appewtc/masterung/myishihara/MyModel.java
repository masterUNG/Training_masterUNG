package appewtc.masterung.myishihara;

/**
 * Created by masterUNG on 1/13/15 AD.
 */
public class MyModel {

    private int intButton, intRadio;

    //Create Interface Class
    public interface OnMyModelChangeListener {
        void onMyModelChangeListener(MyModel myModel);
    }   // Interface

    //Setter myModel
    private OnMyModelChangeListener onMyModelChangeListener;

    public void setOnMyModelChangeListener(OnMyModelChangeListener onMyModelChangeListener) {
        this.onMyModelChangeListener = onMyModelChangeListener;
    }

    public int getIntButton() {
        return intButton;
    }   // getter intButton

    public void setIntButton(int intButton) {
        this.intButton = intButton;

        if (this.onMyModelChangeListener != null) {
            this.onMyModelChangeListener.onMyModelChangeListener(this);
        }


    }   // setter intButton

    public int getIntRadio() {
        return intRadio;
    }   // getter intRadio

    public void setIntRadio(int intRadio) {
        this.intRadio = intRadio;

        if (this.onMyModelChangeListener != null) {
            this.onMyModelChangeListener.onMyModelChangeListener(this);
        }


    }   // setter intRadio

}   // Main Class
