package com.michaelcarrano.detectivedroid.data

import android.content.res.Resources
import com.michaelcarrano.detectivedroid.R
import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import java.util.UUID
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.RETURNS_DEEP_STUBS

class LibraryRepositoryImplTest {

    private lateinit var testSubject: LibraryRepositoryImpl

    private val randomUUID1 = UUID.randomUUID()
    private val randomUUID2 = UUID.randomUUID()

    private val json = "[\n" +
            "  {\n" +
            "    \"id\": \"${randomUUID1}\",\n" +
            "    \"name\": \"foo1\",\n" +
            "    \"source\": \"bar1\",\n" +
            "    \"classPath\": \"baz1\",\n" +
            "    \"createdAt\": 1559254271631,\n" +
            "    \"updatedAt\": 1559254271631\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"${randomUUID2}\",\n" +
            "    \"name\": \"foo2\",\n" +
            "    \"source\": \"bar2\",\n" +
            "    \"classPath\": \"baz2\",\n" +
            "    \"createdAt\": 1559254271631,\n" +
            "    \"updatedAt\": 1559254271631\n" +
            "  }]"
    private val resources = mock<Resources>(defaultAnswer = RETURNS_DEEP_STUBS) {
        on { openRawResource(R.raw.libraries) } doReturn (json.byteInputStream())
    }

    @Before
    fun setUp() {
        testSubject = LibraryRepositoryImpl(resources)
    }

    @Test
    fun `Given libraries, when calling getLibraries, then return list of libraries`() {
        // GIVEN
        val libraries = mutableListOf(
            LibraryEntity(randomUUID1, "foo1", "bar1", "baz1"),
            LibraryEntity(randomUUID2, "foo2", "bar2", "baz2")
        )

        // WHEN
        val testObserver = testSubject.getLibraries().test()

        // THEN
        testObserver.assertValue(libraries)
    }
}
