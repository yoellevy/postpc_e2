package com.example.yoelpc.postpc_e2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class InputDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = InputDialogFragment.class.getCanonicalName();
    private static final String KEY_MESSAGE = "message";

    private InputDialogSendListener listener;

    private BetterEditText inputEditText;

    static InputDialogFragment newInstance(InputDialogSendListener listener, String message) {
        final InputDialogFragment dialogFragment = new InputDialogFragment();
        dialogFragment.listener = listener;
        final Bundle args = new Bundle();
        args.putString(KEY_MESSAGE, message);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    /**
     * For convenience - the calling activity doesn't have to pass TAG.
     */
    public void show(FragmentManager manager) {
        show(manager, TAG);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_input_dialog, container, false);

        inputEditText = view.findViewById(R.id.long_message_input);
        final Button sendButton = view.findViewById(R.id.dialog_send_button);

        inputEditText.setConcernedView(sendButton);
        inputEditText.setText(getArguments().getString(KEY_MESSAGE));

        view.findViewById(R.id.dialog_cancel_button).setOnClickListener(this);
        sendButton.setOnClickListener(this);

        return view;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onResume() {
        super.onResume();
        // This magical code makes the keyboard show up after this dialog is shown.
        // Android is 💩.
        final Window window = getDialog().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        inputEditText.requestFocus();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_send_button) {
            listener.onDialogSendClick(inputEditText.getTrimmedText());
        }

        dismiss();
    }

    public interface InputDialogSendListener {
        void onDialogSendClick(String message);
    }
}
