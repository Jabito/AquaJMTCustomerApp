# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Jabito\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and confirmOrder by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontnote retrofit2.Platform
-dontwarn retrofit3.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions