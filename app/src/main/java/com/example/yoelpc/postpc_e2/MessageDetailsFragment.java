package com.example.yoelpc.postpc_e2;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

public class MessageDetailsFragment extends Fragment {
    private static final String KEY_MESSAGE = "message";

    public OnDeleteButtonClickListener listener;

    static MessageDetailsFragment newInstance(Message message) {
        final MessageDetailsFragment fragment = new MessageDetailsFragment();
        final Bundle args = new Bundle();
        args.putSerializable(KEY_MESSAGE, (Serializable) message);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageDetailsFragment() {
        // Required empty public constructor
    }

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("ConstantConditions")
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_message_details, container, false);

        final Message m = (Message) getArguments().getSerializable(KEY_MESSAGE);
        final String content = m.getContent();
        final SentenceAnalyzer analyzer = new SentenceAnalyzer(content);
        final int wordCount = analyzer.getWordCount();
        final int letterCount = analyzer.getLetterCount();

        ((TextView) view.findViewById(R.id.message_content)).setText('\"' + content + '\"');
        ((TextView) view.findViewById(R.id.author_and_time))
                .setText("- " + m.getAuthor() + ", circa " + m.getTimestamp() + '.');
        ((TextView) view.findViewById(R.id.message_content_analysis))
                .setText(wordCount + " word" + (wordCount != 1 ? 's' : "") + ", " +
                        letterCount + " letter" + (letterCount != 1 ? 's' : "") +
                        ", 1 broken heart.");
        view.findViewById(R.id.delete_message_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteButtonClick(m);
            }
        });
        view.findViewById(R.id.close_message_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCloseButtonClick();
            }
        });

        return view;
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(Message message);

        void onCloseButtonClick();
    }
}
