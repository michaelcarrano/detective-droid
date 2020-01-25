package com.michaelcarrano.detectivedroid.data

import android.content.res.Resources
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before

class LibraryRepositoryImplTest {

    private lateinit var testSubject: LibraryRepositoryImpl
    private val resources = mock<Resources>()

    @Before
    fun setUp() {
        testSubject = LibraryRepositoryImpl(resources)
    }

    // TODO: Fix test
//    fun `Given libraries, when calling getLibraries, then return list of libraries`() {
//        // GIVEN
//        val libraries = mutableListOf(LibraryEntity(), LibraryEntity(), LibraryEntity())
//        whenever(testSubject.parseLibrariesJson(any())).thenReturn(libraries)
//
//        // WHEN
//        val testObserver = testSubject.getLibraries().test()
//
//        // THEN
//        testObserver.assertValue(libraries)
//    }
}
