package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public  static final int MSG_TYPE_LEFT=0;
    public  static final int MSG_TYPE_RIGHT=1;

   private Context mcontext;
   private List<Chat>mchat;
   private String imageurl;

   FirebaseUser fuser;

    public MessageAdapter(Context mcontext, List<Chat> mchat, String imageurl) {
        this.mcontext = mcontext;
        this.mchat = mchat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

        else{
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_left, parent, false);
            return new  MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat=mchat.get(position);

        holder.show_message.setText(chat.getMessage());

        if(imageurl.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mcontext).load(imageurl).into(holder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_image;
        TextView show_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image=itemView.findViewById(R.id.profile_image_ch);
            show_message=itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mchat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
