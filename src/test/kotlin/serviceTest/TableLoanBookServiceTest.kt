package serviceTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.tableLoanBookService.TableLoanBookService
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
        // Test scenario 1: Valid loan book data
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.addLoanBook(1, 1, "01-01-2024")

        // Verify that executeUpdate was called once
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddLoanBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Test scenario 2: Invalid loan book data
        loanBookService.addLoanBook(-1, 1, "invalid-date")

        // Verify that executeUpdate was never called for the second scenario
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteLoanBookValid() {
        // Test scenario 1: Valid loan book ID
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.deleteLoanBook(1)

        // Verify that executeUpdate was called once
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteLoanBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Test scenario 2: Invalid loan book ID
        loanBookService.deleteLoanBook(-1)

        // Verify that executeUpdate was never called for the second scenario
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateLoanBookValid() {
        // Test scenario 1: Valid loan book ID and date
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        loanBookService.updateLoanBook(1, "01-01-2024")

        // Verify that executeUpdate was called once
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateLoanBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Test scenario 2: Invalid loan book ID
        loanBookService.updateLoanBook(-1, "01-01-2024")

        // Verify that executeUpdate was never called for the second scenario
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListLoanBooks() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mocking ResultSet to return two rows
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1, 2)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024", "02-01-2024")

        loanBookService.listLoanBooks()

        // Verify that executeQuery was called once and print was called twice
        verify(mockStatement, times(1)).executeQuery(anyString())

        // Verify that println was called twice
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getInt("id_user")
        verify(mockResultSet, times(2)).getInt("id_book")
        verify(mockResultSet, times(2)).getString("return_date")
    }

    @Test
    fun testListSpecificLoanBookAndIdValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mocking ResultSet to return one row
        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024")

        loanBookService.listSpecificLoanBook(1)

        // Verify that executeQuery was called once and print was called once
        verify(mockStatement, times(1)).executeQuery(anyString())

        // Verify that println was called once
        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getInt("id_user")
        verify(mockResultSet, times(1)).getInt("id_book")
        verify(mockResultSet, times(1)).getString("return_date")
    }

    @Test
    fun testListSpecificLoanBookAndIdInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mocking ResultSet to return one row
        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024")

        // Test scenario 2: Invalid loan book ID
        loanBookService.listSpecificLoanBook(-1)

        // Verify that executeQuery was never called for the second scenario
        verify(mockStatement, never()).executeQuery(anyString())
    }

    @Test
    fun testListMyLoansValid() {
        // Test scenario 1: Valid user ID
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mocking ResultSet to return two rows
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("id_user")).thenReturn(1, 1)
        `when`(mockResultSet.getInt("id_book")).thenReturn(1, 2)
        `when`(mockResultSet.getString("return_date")).thenReturn("01-01-2024", "02-01-2024")

        loanBookService.listMyLoans(1)

        // Verify that executeQuery was called once and print was called twice
        verify(mockStatement, times(1)).executeQuery(anyString())

        // Verify that println was called twice
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getInt("id_user")
        verify(mockResultSet, times(2)).getInt("id_book")
        verify(mockResultSet, times(2)).getString("return_date")
    }

    @Test
    fun testListMyLoansInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Test scenario 2: Invalid user ID
        loanBookService.listMyLoans(-1)

        // Verify that executeQuery was never called for the second scenario
        verify(mockStatement, never()).executeQuery(anyString())
    }
}
