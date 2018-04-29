package com.example.yoelpc.postpc_e2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private ArrayList<Message> messages;
    private OnMessageLongClickListener listener;

    MessageAdapter(OnMessageLongClickListener listener) {
        messages = new ArrayList<>();
        this.listener = listener;
    }
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        final Message m = messages.get(position);
        holder.authorText.setText(m.getAuthor());
        holder.contentText.setText(m.getContent());
        holder.timestampText.setText(m.getTimestamp());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onMessageLongClick(m);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void removeMessage(Message message) {
        final int position = messages.indexOf(message);
        messages.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView authorText, contentText, timestampText;

        ViewHolder(View itemView) {
            super(itemView);
            authorText = itemView.findViewById(R.id.author_text);
            contentText = itemView.findViewById(R.id.content_text);
            timestampText = itemView.findViewById(R.id.time_text);
        }
    }

    public interface OnMessageLongClickListener {
        void onMessageLongClick(Message message);
    }
}

