package com.example.testapp999.model.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.testapp999.model.retrofit.Ads;


@Database(entities = {Ads.class}, version = 1)
public abstract class AdsDataBase extends RoomDatabase {

	private static AdsDataBase instance;

	public abstract AdsDao noteDao();

	public static AdsDataBase getInstance(Context context) {
		if (instance == null) {
			synchronized (AdsDataBase.class) {
				if (instance == null) {
					instance = Room.databaseBuilder(context.getApplicationContext(),
							AdsDataBase.class, "Ads_database.db")
							.fallbackToDestructiveMigration()
//							.addCallback(roomCallback)
							.build();
				}
			}
		}
		return instance;
	}

//	private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//		@Override
//		public void onCreate(@NonNull SupportSQLiteDatabase db) {
//			super.onCreate(db);
//
//			new Thread(()-> {
//				instance.noteDao().insert(new Ads("70901207a3fe43a59bd9bb6e695ebe20.jpg"));
//				instance.noteDao().insert(new Ads("70901207a3fe43a59bd9bb6e695ebe20.jpg"));
//				instance.noteDao().insert(new Ads("70901207a3fe43a59bd9bb6e695ebe20.jpg"));
//			}).start();
//		}
//	};
}

