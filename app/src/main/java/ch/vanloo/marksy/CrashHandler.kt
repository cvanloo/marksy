package ch.vanloo.marksy

import android.content.Context
import android.content.Intent
import android.os.Build
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class CrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        val stringWriter = StringWriter()
        e.printStackTrace(PrintWriter(stringWriter))

        val report = buildString {
            append("**************** CAUSE OF ERROR ****************")
            append(LINE_SEPARATOR)
            append("Message: ")
            append(e.message)
            append(LINE_SEPARATOR)
            append("Stacktrace: ")
            append(stringWriter)
            append(LINE_SEPARATOR)

            append("**************** DEVICE INFORMATION ****************")
            append(LINE_SEPARATOR)
            append("Brand: ")
            append(Build.BRAND)
            append(LINE_SEPARATOR)
            append("Device: ")
            append(Build.DEVICE)
            append(LINE_SEPARATOR)
            append("Model: ")
            append(Build.MODEL)
            append(LINE_SEPARATOR)
            append("ID: ")
            append(Build.ID)
            append(LINE_SEPARATOR)
            append("Product: ")
            append(Build.PRODUCT)
            append(LINE_SEPARATOR)

            append("**************** FIRMWARE ****************")
            append(LINE_SEPARATOR)
            append("SDK: ")
            append(Build.VERSION.SDK_INT)
            append(LINE_SEPARATOR)
            append("Release: ")
            append(Build.VERSION.RELEASE)
            append(LINE_SEPARATOR)
            append("Incremental: ")
            append(Build.VERSION.INCREMENTAL)
            append(LINE_SEPARATOR)
        }

        val intent = Intent(context, CrashActivity::class.java)
        intent.putExtra(CrashActivity.EXTRA_ERROR, report)
        context.startActivity(intent)

        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(255)
    }

    companion object {
        const val LINE_SEPARATOR = "\n"
    }
}