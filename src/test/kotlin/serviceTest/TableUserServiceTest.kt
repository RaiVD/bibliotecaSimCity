package serviceTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.TableUserService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableUserServiceTest {

    private lateinit var userService: TableUserService
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

        userService = TableUserService()
        userService.connection = mockConnection
    }

    @Test
    fun testAddUserValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        userService.addUser("12345678901", "user1", "user1@gmail.com")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddUserNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        userService.addUser("", "", "invalid-email")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteUserValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        userService.deleteUser(1)
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteUserNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        userService.deleteUser(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateValidUserIdAndEmail() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        userService.updateUser(2, "user1@gmail.com")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateInvalidUserIdAndEmail() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        userService.updateUser(-1, "new-email@gmail.com")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateInvalidUserIdAndEmailNotExist() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        userService.updateUser(1, "invalid-email")
        verify(mockStatement, never()).executeUpdate(anyString())
    }


    @Test
    fun testListUsers() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("cpf")).thenReturn("12345678901", "23456789012")
        `when`(mockResultSet.getString("alias")).thenReturn("user1", "user2")
        `when`(mockResultSet.getString("email")).thenReturn("user1@example.com", "user2@example.com")

        userService.listUsers()

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("cpf")
        verify(mockResultSet, times(2)).getString("alias")
        verify(mockResultSet, times(2)).getString("email")
    }

    @Test
    fun testListSpecificUserAndIdValid () {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("cpf")).thenReturn("12345678901")
        `when`(mockResultSet.getString("alias")).thenReturn("user1")
        `when`(mockResultSet.getString("email")).thenReturn("user1@example.com")

        userService.listSpecificUser(1)
        verify(mockStatement, times(1)).executeQuery(anyString())

        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getString("cpf")
        verify(mockResultSet, times(1)).getString("alias")
        verify(mockResultSet, times(1)).getString("email")

    }

    @Test
    fun testListSpecificUserAndIdInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("cpf")).thenReturn("12345678901")
        `when`(mockResultSet.getString("alias")).thenReturn("user1")
        `when`(mockResultSet.getString("email")).thenReturn("user1@example.com")

        userService.listSpecificUser(-1)
        verify(mockStatement, never()).executeQuery(anyString())
    }


    @Test
    fun testUserInfoByAliasAndIdValid() {
        val alias = "user1"
        val sql = "SELECT id, cpf, email FROM users WHERE alias=?"

        `when`(mockConnection.prepareStatement(sql)).thenReturn(mockPreparedStatement)
        `when`(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("cpf")).thenReturn("12345678901")
        `when`(mockResultSet.getString("email")).thenReturn("user1@example.com")

        userService.userInfoByAlias(alias)

        verify(mockPreparedStatement, times(1)).executeQuery()
        verify(mockPreparedStatement, times(1)).setString(1, alias)

        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getString("cpf")
        verify(mockResultSet, times(1)).getString("email")
    }

    @Test
    fun testUserInfoByAliasAndIdInvalid() {
        val alias = "user1"
        val sql = "SELECT id, cpf, email FROM users WHERE alias=?"

        `when`(mockConnection.prepareStatement(sql)).thenReturn(mockPreparedStatement)
        `when`(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)

        userService.userInfoByAlias(alias)


        `when`(mockResultSet.next()).thenReturn(false)

        userService.userInfoByAlias("nonexistentUser")

        verify(mockPreparedStatement, times(2)).executeQuery()

        verify(mockResultSet, never()).getInt("id")
        verify(mockResultSet, never()).getString("cpf")
        verify(mockResultSet, never()).getString("email")
    }
}
