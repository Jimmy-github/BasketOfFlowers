package com.bagelplay.basketofflowers.view;







import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bagelplay.basketofflowers.R;

/**
 * 加载界面的dialog
 */

public class LoadDialog extends Dialog {
	
	private ImageView imageView;
	private AnimationDrawable anim;
	private Context context;
	
	public LoadDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_dialog);
		
		//getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}



	@Override
	public void onBackPressed() {
		dismiss();
	}
}
