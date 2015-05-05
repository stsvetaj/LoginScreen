package fob_solution.com.loginscreen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

    Button cancel;
    Button send;
    EditText acceptLetters;
    EditText acceptNumbers;
    ProgressBar progressBar;
    InputMethodManager inputManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acceptLetters = (EditText) findViewById(R.id.acceptLetters);
        acceptNumbers = (EditText) findViewById(R.id.acceptNumbers);

        acceptLetters.addTextChangedListener(textWatcher);
        acceptNumbers.addTextChangedListener(textWatcher);

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setEnabled(false);

        send = (Button) findViewById(R.id.send);
        send.setEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                cancel.setEnabled(true);
                send.setEnabled(false);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(v==cancel) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }*/

                acceptLetters.setText("");
                acceptNumbers.setText("");
                cancel.setEnabled(false);
                progressBar.setVisibility(View.INVISIBLE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });


        acceptLetters.setFilters((new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if(source.equals("")) {
                            return source;
                        }
                        if(source.toString().matches("[a-zA-Z ]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        }));

        acceptNumbers.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFieldsForEmptyValues();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private void checkFieldsForEmptyValues() {
        send = (Button) findViewById(R.id.send);

        String editLetters = acceptLetters.getText().toString();
        String editNumbers = acceptNumbers.getText().toString();

        if(editLetters.equals("") && editNumbers.equals("")) {
            send.setEnabled(false);
        }
        else if (!editLetters.equals("") && !editNumbers.equals("")) {
            send.setEnabled(true);
        }
    }
}