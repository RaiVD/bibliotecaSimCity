package serviceTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.TableLoanBookService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableLoanBookServiceTest {

    private lateinit var loanBookService: TableLoanBookService
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

        loanBookService = TableLoanBookService()
        loanBookService.connection = mockConnection
    }

    @Test
    fun testAddLoanBookValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.addLoanBook(1, 1, "01-01-2024")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddLoanBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.addLoanBook(-1, 1, "invalid-date")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteLoanBookValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.deleteLoanBook(2)
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteLoanBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.deleteLoanBook(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateLoanBookValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.updateLoanBook(2, "01-01-2024")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateLoanBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.updateLoanBook(-1, "01-01-2024")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListLoanBooks() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1, 2)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024", "02-01-2024")

        loanBookService.listLoanBooks()

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getInt("id_user")
        verify(mockResultSet, times(2)).getInt("id_book")
        verify(mockResultSet, times(2)).getString("return_date")
    }

    @Test
    fun testListSpecificLoanBookAndIdValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024")

        loanBookService.listSpecificLoanBook(2)

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getInt("id_user")
        verify(mockResultSet, times(1)).getInt("id_book")
        verify(mockResultSet, times(1)).getString("return_date")
    }

    @Test
    fun testListSpecificLoanBookAndIdInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024")

        loanBookService.listSpecificLoanBook(-1)
        verify(mockStatement, never()).executeQuery(anyString())
    }

    @Test
    fun testListMyLoansValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(2)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1, 2)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024", "02-01-2024")

        loanBookService.listMyLoans(2)

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getInt("id_user")
        verify(mockResultSet, times(2)).getInt("id_book")
        verify(mockResultSet, times(2)).getString("return_date")
    }

    @Test
    fun testListMyLoansInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        loanBookService.listMyLoans(-1)
        verify(mockStatement, never()).executeQuery(anyString())
    }
}
