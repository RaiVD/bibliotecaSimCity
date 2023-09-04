package serviceTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.tableAuthorService.TableAuthorService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableAuthorServiceTest {

    private lateinit var authorService: TableAuthorService
    private lateinit var mockConnection: Connection
    private lateinit var mockStatement: Statement
    private lateinit var mockPreparedStatement: PreparedStatement
    private lateinit var mockResultSet: ResultSet

    @BeforeEach
    fun setUp() {
        mockConnection = mock(Connection::class.java)
        mockStatement = mock(Statement::class.java)
        mockPreparedStatement = mock(PreparedStatement::class.java)
        mockResultSet = mock(ResultSet::class.java)

        authorService = TableAuthorService()
        authorService.connection = mockConnection
    }

    @Test
    fun testAddAuthorValid() {
        // Test scenario 1: Valid user data
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.addAuthor("user1")

        // Verify that executeUpdate was called once
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddAuthorNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Test scenario 2: Invalid user data
        authorService.addAuthor("")

        // Verify that executeUpdate was never called for the second scenario
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteAuthorValid() {
        // Test scenario 1: Valid user ID
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.deleteAuthor(1)

        // Verify that executeUpdate was called once
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteAuthorNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Test scenario 2: Invalid user ID
        authorService.deleteAuthor(-1)

        // Verify that executeUpdate was never called for the second scenario
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateValidAuthor() {
        // Test scenario 1: Valid user ID and email
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.updateAuthor(2, "user1")

        // Verify that executeUpdate was called once
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateInvalidAuthor() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Test scenario 2: Invalid user ID
        authorService.updateAuthor(-1, "newNameTest")

        // Verify that executeUpdate was never called for the second scenario
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateInvalidAuthorNull() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Test scenario 3: Invalid email
        authorService.updateAuthor(1, " ")

        // Verify that executeUpdate was never called for the third scenario
        verify(mockStatement, never()).executeUpdate(anyString())
    }


    @Test
    fun testListAuthor() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mocking ResultSet to return two rows
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_author")).thenReturn("user1", "user2")

        authorService.listAuthors()

        // Verify that executeQuery was called once and print was called twice
        verify(mockStatement, times(1)).executeQuery(anyString())

        // Verify that println was called twice
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("name_author")
    }

    @Test
    fun testListSpecificAuthorAndIdValid () {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mocking ResultSet to return two rows
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_author")).thenReturn("user1", "user2")

        authorService.listSpecificAuthor(1)

        // Verify that executeQuery was called once and print was called once
        verify(mockStatement, times(1)).executeQuery(anyString())

        // Verify that println was called twice
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("name_author")
    }

    @Test
    fun testListSpecificAuthorAndIdInvalid () {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mocking ResultSet to return two rows
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_author")).thenReturn("user1", "user2")

        // Test scenario 2: Invalid user ID
        authorService.listSpecificAuthor(-1)

        // Verify that executeQuery was never called for the second scenario
        verify(mockStatement, never()).executeQuery(anyString())
    }
}



