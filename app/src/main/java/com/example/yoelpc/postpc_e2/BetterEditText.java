package com.example.yoelpc.postpc_e2;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * For the lack of a better name, this needs to be documented...
 * This custom EditText will make sure that it keeps the trimmed version of its content.
 * it'll invoke listeners iff the trimmed text is not empty.
 */
public class BetterEditText extends AppCompatEditText {
    /**
     * An optional view that will be enabled iff the text is not empty.
     */
    private View concernedView;
    private String currentText;
    private OnKeyboardSendClickListener listener;

    public BetterEditText(Context context) {
        super(context);
        init();
    }

    /**
     * This is the constructor we expect to be called as the layout is inflated from the XML file.
     * However, it's good practice to invoke init() in the other 2 constructors.
     */
    public BetterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BetterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        currentText = "";

        addTextChangedListener(new TextChangedListener() {
            @Override
            void onTextChanged(CharSequence s) {
                currentText = s.toString().trim();
                if (concernedView != null) {
                    concernedView.setEnabled(currentText.length() != 0);
                }
            }
        });

        if (getImeOptions() != EditorInfo.IME_ACTION_SEND) {
            return;
        }

        // Set a listener for the keyboard's send button
        setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                currentText = getText().toString().trim();

                if (listener != null &&
                        actionId == EditorInfo.IME_ACTION_SEND && currentText.length() != 0) {
                    listener.onKeyboardSendClick(currentText);
                    return true;
                }

                return false;
            }
        });
    }

    public void setConcernedView(View concernedView) {
        (this.concernedView = concernedView).setEnabled(!currentText.isEmpty());
    }

    public void setOnKeyboardSendClickListener(OnKeyboardSendClickListener listener) {
        this.listener = listener;
    }

    public String getTrimmedText() {
        return currentText;
    }

    public interface OnKeyboardSendClickListener {
        void onKeyboardSendClick(String text);
    }
}
