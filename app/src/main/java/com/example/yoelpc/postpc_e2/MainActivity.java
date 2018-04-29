package com.example.yoelpc.postpc_e2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements InputDialogFragment.InputDialogSendListener,
        BetterEditText.OnKeyboardSendClickListener,
        MessageAdapter.OnMessageLongClickListener,
        MessageDetailsFragment.OnDeleteButtonClickListener {

    private BetterEditText messageEditText;
    private MessageAdapter messageAdapter;
    private RecyclerView messageList;

    private String authorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAuthorNameDialog();

        setupMessageEditText();

        setupMessageList();
    }

    /**
     * Show a dialog that requests the user's name.
     * If input is empty, the default author name is used (which happens to be the EditText's hint).
     */
    private void showAuthorNameDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.author_name_dialog_title);
        alert.setView(R.layout.author_name_dialog);
        alert.setCancelable(false);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText nameInput = ((AlertDialog) dialog).findViewById(R.id.author_name_input);
                final String input = nameInput.getText().toString().trim();
                authorName = input.isEmpty() ? nameInput.getHint().toString() : input;
            }
        });

        alert.show();
    }

    private void setupMessageEditText() {
        messageEditText = findViewById(R.id.message_input);
        messageEditText.setConcernedView(findViewById(R.id.send_button));
        messageEditText.setOnKeyboardSendClickListener(this);
        messageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Close the MessageDetailsFragment when the keyboard is about to show up
                if (hasFocus) {
                    getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    /**
     * Sets up the message RecyclerView and its adapter.
     */
    private void setupMessageList() {
        messageList = findViewById(R.id.message_list);
        messageList.setHasFixedSize(true);
        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        messageList.setAdapter(messageAdapter = new MessageAdapter(this));
    }

    /**
     * This method is tied to the send button's OnClick listener (see respective layout XML file).
     */
    public void onSendClick(@Nullable View view) {
        sendMessage(messageEditText.getTrimmedText());
    }

    /**
     * In this activity, using the IMEOptions property of EditText, the keyboard has a "send" button
     * instead of "enter". Thus, we listen to its click events to send the message.
     *
     * @param text The trimmed content of the EditText. Can be assumed that it's not empty.
     */
    @Override
    public void onKeyboardSendClick(String text) {
        sendMessage(text);
    }

    public void openInputDialog(View view) {
        InputDialogFragment.newInstance(this, messageEditText.getTrimmedText())
                .show(getSupportFragmentManager());
    }

    @Override
    public void onDialogSendClick(String message) {
        sendMessage(message);
    }

    /**
     * Adds a message to the list, empties the input field and makes sure that said message is visible.
     *
     * @param messageString The message to add to the list.
     */
    public void sendMessage(String messageString) {
        messageAdapter.addMessage(new Message(authorName, messageString));
        messageEditText.setText(null);
        messageList.smoothScrollToPosition(messageAdapter.getItemCount());
    }

    @Override
    public void onMessageLongClick(Message message) {
        final MessageDetailsFragment fragment = MessageDetailsFragment.newInstance(message);
        fragment.setOnDeleteButtonClickListener(this);

        hideKeyboard();
        messageEditText.clearFocus();

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container_view, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteButtonClick(Message message) {
        getSupportFragmentManager().popBackStack();
        messageAdapter.removeMessage(message);
    }

    @Override
    public void onCloseButtonClick() {
        getSupportFragmentManager().popBackStack();
    }

    private void hideKeyboard() {
        final View view = findViewById(android.R.id.content);
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
