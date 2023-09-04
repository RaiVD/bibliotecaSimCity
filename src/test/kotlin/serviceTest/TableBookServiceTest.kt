package serviceTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.tableBookService.TableBookService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableBookServiceTest {

    private lateinit var bookService: TableBookService
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

        bookService = TableBookService()
        // Defina a conexão do serviço de livro como a conexão mockada
        bookService.connection = mockConnection
    }

    @Test
    fun testAddBookValid() {
        // Teste de cenário 1: Dados válidos do livro
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        bookService.addBook("1234567890123", "Livro1", 1)

        // Verifique se executeUpdate foi chamado uma vez
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Teste de cenário 2: Dados inválidos do livro
        bookService.addBook("", "", -1)

        // Verifique se executeUpdate nunca foi chamado para o segundo cenário
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteBookValid() {
        // Teste de cenário 1: ID de livro válido
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        bookService.deleteBook(1)

        // Verifique se executeUpdate foi chamado uma vez
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteBookNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Teste de cenário 2: ID de livro inválido
        bookService.deleteBook(-1)

        // Verifique se executeUpdate nunca foi chamado para o segundo cenário
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateBookValid() {
        // Teste de cenário 1: ID de livro válido e dados válidos
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        bookService.updateBook(1, "1234567890123", "Livro Atualizado", 2)

        // Verifique se executeUpdate foi chamado uma vez
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateBookInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Teste de cenário 2: ID de livro inválido
        bookService.updateBook(-1, "1234567890123", "Livro Atualizado", 2)

        // Verifique se executeUpdate nunca foi chamado para o segundo cenário
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateBookInvalidData() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        // Teste de cenário 3: Dados inválidos do livro
        bookService.updateBook(1, "", "", -1)

        // Verifique se executeUpdate nunca foi chamado para o terceiro cenário
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListBooks() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mock ResultSet para retornar duas linhas
        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("isbn")).thenReturn("1234567890123", "2345678901234")
        `when`(mockResultSet.getString("name_book")).thenReturn("Livro1", "Livro2")
        `when`(mockResultSet.getInt("id_author")).thenReturn(1, 2)

        bookService.listBooks()

        // Verifique se executeQuery foi chamado uma vez e println foi chamado duas vezes
        verify(mockStatement, times(1)).executeQuery(anyString())

        // Verifique se println foi chamado duas vezes
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("isbn")
        verify(mockResultSet, times(2)).getString("name_book")
        verify(mockResultSet, times(2)).getInt("id_author")
    }

    @Test
    fun testListSpecificBookValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mock ResultSet para retornar uma linha
        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("isbn")).thenReturn("1234567890123")
        `when`(mockResultSet.getString("name_book")).thenReturn("Livro1")
        `when`(mockResultSet.getInt("id_author")).thenReturn(1)

        bookService.listSpecificBook(1)

        // Verifique se executeQuery foi chamado uma vez e println foi chamado uma vez
        verify(mockStatement, times(1)).executeQuery(anyString())

        // Verifique se println foi chamado uma vez
        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getString("isbn")
        verify(mockResultSet, times(1)).getString("name_book")
        verify(mockResultSet, times(1)).getInt("id_author")
    }

    @Test
    fun testListSpecificBookInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        // Mock ResultSet para retornar uma linha
        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("isbn")).thenReturn("1234567890123")
        `when`(mockResultSet.getString("name_book")).thenReturn("Livro1")
        `when`(mockResultSet.getInt("id_author")).thenReturn(1)

        // Teste de cenário 2: ID de livro inválido
        bookService.listSpecificBook(-1)

        // Verifique se executeQuery nunca foi chamado para o segundo cenário
        verify(mockStatement, never()).executeQuery(anyString())
    }
}
