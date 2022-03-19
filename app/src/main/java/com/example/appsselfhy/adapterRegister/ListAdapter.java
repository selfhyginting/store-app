package com.example.appsselfhy.adapterRegister;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appsselfhy.ReviewActivity;
import com.example.appsselfhy.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<User> {
    private Activity contex;
    private List<User> userList;
    public ListAdapter(ReviewActivity context, List<User> userList) {
        super(context, R.layout.list_user_view, userList);
        this.contex = context;
        this.userList = userList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = contex.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_user_view, null, true);
        TextView name = listViewItem.findViewById(R.id.name);
        TextView messageReview = listViewItem.findViewById(R.id.messageReviewet);
        User user = userList.get(position);
        name.setText(user.getName());
        messageReview.setText(user.getmessageReview());
        return listViewItem;
    }
}
