package com.webpuzzle;

//import java.net.URL;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
////import com.fima.cardsui.objects.Card;
//import com.webpuzzle.async.BitmapAsync;
//
//public class MyImageCard extends Card {
//
//	public MyImageCard(String title, String desc, URL image){
//		super(title, desc, image);
//	}
//
//	@Override
//	public View getCardContent(Context context) {
//		View view = LayoutInflater.from(context).inflate(R.layout.card_picture, null);
//
//		((TextView) view.findViewById(R.id.title)).setText(title);
//		((TextView) view.findViewById(R.id.description)).setText(desc);
////		ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
//		new BitmapAsync(((ImageView) view.findViewById(R.id.imageView1))).execute(image);
//		
//		return view;
//	}
//}
