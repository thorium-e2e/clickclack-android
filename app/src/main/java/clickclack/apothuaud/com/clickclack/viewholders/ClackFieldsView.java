package clickclack.apothuaud.com.clickclack.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import clickclack.apothuaud.com.clickclack.R;

@SuppressWarnings("unused")
public class ClackFieldsView {

    private Context context;

    private EditText key;
    private EditText value;
    // ToDo: add delete fields btn

    private View view;

    public ClackFieldsView(Context context) {
        this.context = context;
        init();
    }

    public View getView() {
        return view;
    }

    @SuppressLint("InflateParams")
    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(R.layout.row_clack_fields, null);
        this.key = view.findViewById(R.id.create_clack_key);
        this.value = view.findViewById(R.id.create_clack_value);
    }

    public EditText getKey() {
        return key;
    }

    public void setKey(EditText key) {
        this.key = key;
    }

    public EditText getValue() {
        return value;
    }

    public void setValue(EditText value) {
        this.value = value;
    }
}
