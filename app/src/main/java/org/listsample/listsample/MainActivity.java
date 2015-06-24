package org.listsample.listsample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

   private Button btnDialog;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      initViews();
   }

   private void initViews() {
      btnDialog = (Button) findViewById(R.id.btn_show_dialog);
      btnDialog.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showListDialog();
         }
      });
   }

   private void showListDialog() {
      FragmentManager fragmentManager = getSupportFragmentManager();
      if (fragmentManager.findFragmentByTag(ListDialogFragment.DIALOG_TAG) != null) {
         return;
      }
      ListDialogFragment dialogFragment = new ListDialogFragment();
      dialogFragment.show(fragmentManager, ListDialogFragment.DIALOG_TAG);
   }
}
