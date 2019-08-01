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

    @Provides
    @Singleton
    fun provideContext(app: FilmowyApp): Context = app

    @Provides
    @Singleton
    fun provideSharedPref(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideLoggedUser(preferences: SharedPreferences): UserData {
        if (preferences.contains(UserData.KEY)) {
            return Gson().fromJson(preferences.getString(UserData.KEY, ""), UserData::class.java)
        }
        return UserData(-1, "", "", "", 0, null)
    }

    @Provides
    @Singleton
    fun providePicasso(context: Context): Picasso = Picasso.Builder(context)
        .loggingEnabled(BuildConfig.DEBUG)
        .build()
}
