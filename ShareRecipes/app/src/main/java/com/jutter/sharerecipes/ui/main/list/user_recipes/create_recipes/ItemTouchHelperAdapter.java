package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}