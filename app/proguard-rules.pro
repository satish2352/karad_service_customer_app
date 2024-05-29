# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}





-keep class org.json.** { *; }

# AndroidX AppCompat
-keep class androidx.appcompat.** { *; }
-keep class androidx.core.** { *; }
-keep class androidx.fragment.app.** { *; }

# ConstraintLayout
-keep class androidx.constraintlayout.** { *; }

# Legacy Support
-keep class androidx.legacy.** { *; }

# Annotations (usually not required to keep)
-keep class org.jetbrains.annotations.** { *; }

# Firebase Messaging
-keep class com.google.firebase.messaging.** { *; }
-keep class com.google.firebase.iid.** { *; }
-keep class com.google.firebase.** { *; }
-keepnames class com.google.firebase.messaging.** { *; }
-keepnames class com.google.firebase.iid.** { *; }

# VectorDrawable
-keep class androidx.vectordrawable.** { *; }

# Material Components
-keep class com.google.android.material.** { *; }

# Material Stepper
-keep class com.stepstone.stepper.** { *; }

# CircleImageView
-keep class de.hdodenhof.circleimageview.** { *; }

# Picasso
-keep class com.squareup.picasso.** { *; }
-keep interface com.squareup.picasso.** { *; }
-keepclasseswithmembers class * {
    @com.squareup.picasso.Picasso$LoadedFrom <fields>;
}

# Glide
-keep class com.bumptech.glide.** { *; }
-keep interface com.bumptech.glide.** { *; }
-keepclasseswithmembers class * {
    @com.bumptech.glide.annotation.GlideModule <methods>;
}

# Slider (Daimajia Slider)
-keep class com.daimajia.slider.library.** { *; }
-keep class com.nineoldandroids.** { *; }

# Android GIF Drawable
-keep class pl.droidsonroids.gif.** { *; }

# Floating Toolbar
-keep class com.github.rubensousa.floatingtoolbar.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keepclassmembers class okhttp3.** {
    *;
}
-keepclassmembers class retrofit2.** {
    *;
}
-keepattributes Signature
-keepattributes Exceptions

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature

# SimpleArcLoader
-keep class com.leo.simplearcloader.** { *; }

# OkHttp Logging Interceptor
-keep class okhttp3.logging.HttpLoggingInterceptor { *; }



-keep class com.example.karaduser.ModelList.** { *; }

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}
