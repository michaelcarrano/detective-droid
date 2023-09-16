package com.michaelcarrano.detectivedroid.domain

import android.content.Context
import com.michaelcarrano.detectivedroid.data.LibraryRepository
import com.michaelcarrano.detectivedroid.data.model.LibraryNotFoundEntity
import com.michaelcarrano.detectivedroid.presentation.model.LibraryMapper
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ScanAppUseCaseTest {

    private lateinit var testSubject: ScanAppUseCase

    private val context = mock<Context>()
    private val repo = mock<LibraryRepository>()
    private val mapper = mock<LibraryMapper>()

    @Before
    fun setUp() {
        testSubject = ScanAppUseCase(context, repo, mapper)
    }

    // TODO: Resolve issue with ClassLoader
    // org.mockito.exceptions.misusing.WrongTypeOfReturnValue: LibraryEntity cannot be returned by getClassLoader() getClassLoader() should return ClassLoader
//    fun `Given app to scan with known libraries, when scanning app, then return detected libraries`() {
//        // GIVEN
//        val app = "app.with.known.libraries"
//        val libraries = listOf(LibraryEntity())
//        whenever(repo.getLibraries()).thenReturn(Single.just(libraries))
//        whenever(context.createPackageContext(any(), any())).thenReturn(context)
//        whenever(testSubject.detectLibrary(app, libraries.first())).thenReturn(libraries.first())
//
//        // WHEN
//        val testObserver = testSubject.scanApp(app).test()
//
//        // THEN
//        testObserver.assertValue(listOf(mapper.mapToUiModel(libraries.first())))
//    }

    @Test
    fun `Given app to scan with no known libraries, when scanning app, then return no libraries`() {
        // GIVEN
        val app = "app.with.no.known.libraries"
        val libraries = listOf(LibraryNotFoundEntity)
        whenever(repo.getLibraries()).thenReturn(Single.just(libraries))

        // WHEN
        val testObserver = testSubject.scanApp(app).test()

        // THEN
        testObserver.assertValue(emptyList())
    }
}
