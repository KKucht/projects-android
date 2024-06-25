#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_eti_kuchta_lab5c_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL Java_eti_kuchta_lab5c_MainActivity_stringMyList
        (JNIEnv * env, jobject thiz, jintArray  x)
{
    int len = env->GetArrayLength(x);
    int *body = env->GetIntArrayElements( x, 0);
    for (int i = 0; i < len; i++) {
        for (int j = 0; j < len; j++) {
            if (body[i] < body[j]) {
                int y = body[i];
                body[i] = body[j];
                body[j] = y;
            }
        }
    }
    std::string output = "";
    for (int i = 0; i < len; i++) {
        output = output + std::to_string(body[i]) +" ";
    }
    return env->NewStringUTF(output.c_str());
}

