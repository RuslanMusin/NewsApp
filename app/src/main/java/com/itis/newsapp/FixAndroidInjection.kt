package com.itis.newsapp

import android.app.Activity
import android.app.Service
import androidx.fragment.app.Fragment
import com.itis.newsapp.logger.Logger
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector

object FixAndroidInjection {

    fun inject(activity: Activity) {
        val application = activity.applicationContext
        if (application is HasActivityInjector) {
            val activityInjector = application.activityInjector()
            checkNotNull(activityInjector) { String.format("%s.activityInjector() returned null", application.javaClass.name) }
            activityInjector.inject(activity)
        } else {
            throw RuntimeException(
                String.format(
                    "%s does not implement %s",
                    application.javaClass.canonicalName,
                    HasActivityInjector::class.java.canonicalName
                )
            )
        }
    }


    fun inject(service: Service) {
        val application = service.applicationContext
        if (application !is HasServiceInjector) {
            throw RuntimeException(
                String.format(
                    "%s does not implement %s",
                    application.javaClass.getCanonicalName(),
                    HasServiceInjector::class.java.canonicalName
                )
            )
        } else {
            val serviceInjector = application.serviceInjector()
            checkNotNull(serviceInjector) {
                String.format("%s.serviceInjector() returned null", application.javaClass.name)
            }
            serviceInjector.inject(service)
        }
    }

    fun inject(fragment: Fragment) {
        val hasSupportFragmentInjector = findHasFragmentInjector(fragment)
        Logger.d("FixAndroidInjection", String.format("An injector for %s was found in %s", fragment.javaClass.canonicalName, hasSupportFragmentInjector.javaClass.canonicalName))
        val fragmentInjector = hasSupportFragmentInjector.supportFragmentInjector()
        checkNotNull(fragmentInjector) { String.format("%s.supportFragmentInjector() returned null", hasSupportFragmentInjector.javaClass.name) }
        fragmentInjector.inject(fragment)
    }

    private fun findHasFragmentInjector(fragment: Fragment): HasSupportFragmentInjector {
        var parentFragment: Fragment? = fragment.parentFragment

        while (parentFragment != null) {
            if (parentFragment is HasSupportFragmentInjector) {
                return parentFragment
            } else {
                parentFragment = parentFragment.parentFragment
            }
        }

        val activity = fragment.activity

        if (activity is HasSupportFragmentInjector) {
            return activity
        }

        val application = activity?.applicationContext

        if (application is HasSupportFragmentInjector) {
            return application
        }
        val error =  IllegalArgumentException(String.format("No injector was found for %s", fragment.javaClass.canonicalName))
        throw  error
    }


}