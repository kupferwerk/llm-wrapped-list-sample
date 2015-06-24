package org.listsample.listsample;


public class ItemClickedEvent {

   private final int position;

   public ItemClickedEvent(int position){
      this.position = position;
   }

   public int getPosition() {
      return position;
   }
}
