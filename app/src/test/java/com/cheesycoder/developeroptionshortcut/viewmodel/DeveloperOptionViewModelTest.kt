package com.cheesycoder.developeroptionshortcut.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheesycoder.developeroptionshortcut.controller.DontKeepActivitiesController
import com.cheesycoder.developeroptionshortcut.model.DontKeepActivitiesSource
import com.cheesycoder.developeroptionshortcut.model.ErrorCode
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeveloperOptionViewModelTest {

    private var viewModel: DeveloperOptionViewModel? = null
    private val dontKeepActivitiesController: DontKeepActivitiesController = mockk()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = DeveloperOptionViewModel(dontKeepActivitiesController)
    }

    @After
    fun tearDown() {
        viewModel = null
    }

    @Test
    fun `onResume - should fetch true and add listener`() {
        every { dontKeepActivitiesController.isSet } returns true
        every { dontKeepActivitiesController.addStatusListener(any()) } just Runs

        viewModel!!.onResume()

        assertEquals(
            viewModel!!.dontKeepActivityStatus.value,
            DontKeepActivitiesSource(false, true)
        )
        verify(exactly = 1) { dontKeepActivitiesController.addStatusListener(any()) }
    }

    @Test
    fun `onResume - should fetch false and add listener`() {
        every { dontKeepActivitiesController.isSet } returns false
        every { dontKeepActivitiesController.addStatusListener(any()) } just Runs

        viewModel!!.onResume()

        assertEquals(
            viewModel!!.dontKeepActivityStatus.value,
            DontKeepActivitiesSource(false, false)
        )
        verify(exactly = 1) { dontKeepActivitiesController.addStatusListener(any()) }
    }

    @Test
    fun `onPause - should remove status listener`() {
        every { dontKeepActivitiesController.removeStatusListener(any()) } just Runs

        viewModel!!.onPause()

        verify(exactly = 1) { dontKeepActivitiesController.removeStatusListener(any()) }
    }

    @Test
    fun `dontKeepActivities - success`() {
        every { dontKeepActivitiesController::isSet.set(any()) } just Runs

        viewModel!!.dontKeepActivities(true)

        assertNull(viewModel!!.developerOptionError.value)
    }

    @Test
    fun `dontKeepActivities - security exception`() {
        every { dontKeepActivitiesController::isSet.set(any()) } throws SecurityException()

        viewModel!!.dontKeepActivities(true)

        assertEquals(viewModel!!.developerOptionError.value, ErrorCode.SETUP_REQUIRED.asEvent())
    }

    @Test
    fun `dontKeepActivities - class not found exception`() {
        every { dontKeepActivitiesController::isSet.set(any()) } throws ClassNotFoundException()

        viewModel!!.dontKeepActivities(true)

        assertEquals(viewModel!!.developerOptionError.value, ErrorCode.API_INCOMPATIBLE.asEvent())
    }

    @Test(expected = Exception::class)
    fun `dontKeepActivities - other exception`() {
        every { dontKeepActivitiesController::isSet.set(any()) } throws Exception()

        viewModel!!.dontKeepActivities(true)
    }
}
