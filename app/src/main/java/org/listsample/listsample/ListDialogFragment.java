package org.listsample.listsample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ListDialogFragment extends DialogFragment {

   private class MessageHandler extends Handler {
      @Override
      public void handleMessage(Message msg) {
         super.handleMessage(msg);
         removeTestItemsFromList();
      }
   }

   public static final String DIALOG_TAG = ListDialogFragment.class.getSimpleName();
   public static final String INSERT_TEST = "> Insert Test";

   private ListAdapter adapter;
   private List<String> listItems = new ArrayList<>();
   private RecyclerView recyclerView;
   private MessageHandler timeoutHandler;

   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      final Dialog dialog = super.onCreateDialog(savedInstanceState);
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      return dialog;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.dialog, container);
      fillTestListItems();
      restrictHeight(view);
      initFields(view);
      initRecyclerView();
      return view;
   }

   private void fillTestListItems() {
      listItems.add("One");
      listItems.add("Two");
      listItems.add("Three");
      listItems.add("Four");
      listItems.add("Five");
      listItems.add("Six");
      listItems.add("Seven");
      listItems.add("Eight");
   }

   private void restrictHeight(final View view) {
      view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
         @Override
         public boolean onPreDraw() {
            view.getViewTreeObserver().removeOnPreDrawListener(this);
            int dialogMaxHeight = getResources().getDimensionPixelSize(R.dimen.dialog_max_height);
            if (view.getHeight() > dialogMaxHeight) {
               ViewGroup.LayoutParams params = view.getLayoutParams();
               params.height = dialogMaxHeight;
               view.setLayoutParams(params);
            }
            return true;
         }
      });
   }

   private void initFields(View view) {
      recyclerView = (RecyclerView) view.findViewById(R.id.list);
   }

   private void initRecyclerView() {
      RecyclerView.LayoutManager layoutManager =
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
      recyclerView.setLayoutManager(layoutManager);
      adapter = new ListAdapter(listItems);
      recyclerView.setAdapter(adapter);
   }

   @Override
   public void onResume() {
      super.onResume();
      EventBus.getDefault().register(this);
      timeoutHandler = new MessageHandler();
   }

   @Override
   public void onPause() {
      super.onPause();
      EventBus.getDefault().unregister(this);
      timeoutHandler = null;
   }

   @Override
   public void onDismiss(DialogInterface dialog) {
      super.onDismiss(dialog);
      removeTestItemsFromList();
   }

   public void onEvent(ItemClickedEvent event) {
      int position = event.getPosition();
      String selectedItem = listItems.get(position);
      if (selectedItem.equals(INSERT_TEST)) {
         return;
      }
      int insertPosition = position + 1;
      removeTestItemsFromList();
      addTestItemForPosition(insertPosition);
   }

   private void addTestItemForPosition(int position) {
      listItems.add(position, INSERT_TEST);
      adapter.notifyItemInserted(position);
      timeoutHandler.sendMessageDelayed(timeoutHandler.obtainMessage(), 3000);
   }

   private void removeTestItemsFromList() {
      if (adapter == null) {
         return;
      }
      List<String> items = adapter.getItems();
      int index = 0;
      for (Iterator<String> iterator = items.listIterator(); iterator.hasNext(); index++) {
         String text = iterator.next();
         if (text.equals(INSERT_TEST)) {
            iterator.remove();
            adapter.notifyItemRemoved(index);
         }
      }
   }
}
