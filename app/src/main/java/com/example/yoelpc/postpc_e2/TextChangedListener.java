package com.example.yoelpc.postpc_e2;

import android.text.Editable;
import android.text.TextWatcher;

abstract class TextChangedListener implements TextWatcher {
    abstract void onTextChanged(CharSequence s);


    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Leave empty
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged(s);
    }

    @Override
    public final void afterTextChanged(Editable s) {
        // Leave empty
    }
}
