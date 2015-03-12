package com.deleteItem;

public interface OnDeleteListioner {
	public abstract boolean isCandelete(int position);
	public abstract void onDelete(int ID);
	public abstract void onBack();
	public abstract void onClick(int position);
}
