package ir.nilva.reliablemessaging

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import ir.nilva.reliablemessaging.work.MessageWorker
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse

import org.junit.runner.RunWith

import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers
import org.junit.*
import org.junit.Assert.assertThat
import timber.log.Timber
import java.util.concurrent.TimeUnit
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class MessageWorkerTest {

    private lateinit var webServer: MockWebServer
    private lateinit var context: Context

    @Before
    @Throws(Exception::class)
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
        val config = Configuration.Builder()
            // Set log level to Log.DEBUG to make it easier to debug
            .setMinimumLoggingLevel(Log.DEBUG)
            // Use a SynchronousExecutor here to make it easier to write tests
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        webServer = MockWebServer()
        webServer.start(8080)
    }

    @Test
    fun basicTest() {

        webServer.enqueue(MockResponse().setResponseCode(200))

        val workRequest =
            OneTimeWorkRequestBuilder<MessageWorker>()
                .setInputData(
                    Data.Builder()
                        .putString(KEY_URL, webServer.url("").toString())
                        .putString("data", "message")
                        .build()
                ).setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    BACK_OFF_DELAY,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance().enqueue(workRequest)

        val res = WorkManager.getInstance().getWorkInfoById(workRequest.id).get()

        assertThat(res.state, `is`(WorkInfo.State.ENQUEUED))


    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer.shutdown()
    }
}