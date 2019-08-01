package io.github.mklkj.filmowy.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.github.mklkj.filmowy.BuildConfig
import io.github.mklkj.filmowy.FilmowyApp
import io.github.mklkj.filmowy.api.pojo.UserData
import javax.inject.Singleton

@Module
internal class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: FilmowyApp): Context = app

    @Singleton
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideLoggedUser(preferences: SharedPreferences): UserData {
        if (preferences.contains(UserData.KEY)) {
            return Gson().fromJson(preferences.getString(UserData.KEY, ""), UserData::class.java)
        }
        return UserData(-1, "", "", "", 0, null)
    }

    @Singleton
    @Provides
    fun providePicasso(context: Context): Picasso = Picasso.Builder(context)
        .loggingEnabled(BuildConfig.DEBUG)
        .build()
}
