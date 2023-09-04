package serviceTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.TableAuthorService
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
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)


        authorService.addAuthor("user1")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddAuthorNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.addAuthor("")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteAuthorValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.deleteAuthor(1)
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteAuthorNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.deleteAuthor(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateValidAuthor() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.updateAuthor(2, "user1")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateInvalidAuthor() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.updateAuthor(-1, "newNameTest")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateInvalidAuthorNull() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        authorService.updateAuthor(1, " ")
        verify(mockStatement, never()).executeUpdate(anyString())
    }


    @Test
    fun testListAuthor() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_author")).thenReturn("user1", "user2")

        authorService.listAuthors()

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("name_author")
    }

    @Test
    fun testListSpecificAuthorAndIdValid () {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_author")).thenReturn("user1", "user2")

        authorService.listSpecificAuthor(1)

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("name_author")
    }

    @Test
    fun testListSpecificAuthorAndIdInvalid () {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_author")).thenReturn("user1", "user2")

        authorService.listSpecificAuthor(-1)
        verify(mockStatement, never()).executeQuery(anyString())
    }
}



