package org.listsample.listsample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.event.EventBus;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

   public class ViewHolder extends RecyclerView.ViewHolder {

      TextView text;

      public ViewHolder(View itemView) {
         super(itemView);
         this.text = (TextView) itemView.findViewById(R.id.text);
      }
   }

   private final List<String> items;

   public ListAdapter(List<String> items) {
      this.items = items;
   }

   public List<String> getItems() {
      return items;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
      View rootLayout =
            LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
      return new ViewHolder(rootLayout);
   }

   @Override
   public void onBindViewHolder(ViewHolder viewHolder, final int position) {
      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            EventBus.getDefault().post(new ItemClickedEvent(position));
         }
      });
      viewHolder.text.setText(items.get(position));
   }

   @Override
   public int getItemCount() {
      return items.size();
   }
}
